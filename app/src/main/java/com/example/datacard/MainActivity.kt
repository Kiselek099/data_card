package com.example.datacard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    var GALLERY_REQUEST = 302
    lateinit var mainTB:Toolbar
    lateinit var nameET: EditText
    lateinit var surnameET: EditText
    lateinit var dataET: EditText
    lateinit var phoneET: EditText
    lateinit var photoIV: ImageView
    lateinit var saveBTN: Button
    var photoUri: Uri? = null
    var person: Person? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        saveBTN.setOnClickListener {
            setPerson()
            val intent = Intent(this, ActivityTwo::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
        photoIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
    }

    private fun setPerson() {
        val name = nameET.text.toString()
        val surname = surnameET.text.toString()
        val data = dataET.text.toString()
        val phone = phoneET.text.toString()
        val photo = photoUri.toString()
        person = Person(name, surname, data, phone, photo)
    }

    private fun init() {
        nameET = findViewById(R.id.nameET)
        surnameET = findViewById(R.id.surnameET)
        dataET = findViewById(R.id.dataET)
        phoneET = findViewById(R.id.phoneET)
        photoIV = findViewById(R.id.photoIV)
        saveBTN = findViewById(R.id.saveBTN)
        mainTB = findViewById(R.id.mainTB)
        setSupportActionBar(mainTB)
        title = "Карта пользователя"
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                photoUri = data?.data
                photoIV.setImageURI(photoUri)
            }
        }

    }
}