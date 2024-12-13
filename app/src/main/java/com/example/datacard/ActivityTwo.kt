package com.example.datacard

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class ActivityTwo : AppCompatActivity() {
    var person:Person?=null
    lateinit var nameTV:TextView
    lateinit var surnameTV:TextView
    lateinit var phoneTV:TextView
    lateinit var dataTV:TextView
    lateinit var photoIV:ImageView
    lateinit var mainTB:Toolbar
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_two)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameTV=findViewById(R.id.nameTV)
        surnameTV=findViewById(R.id.surnameTV)
        phoneTV=findViewById(R.id.phoneTV)
        dataTV=findViewById(R.id.dataTV)
        photoIV=findViewById(R.id.photoIV)
        mainTB=findViewById(R.id.mainTB)
        setSupportActionBar(mainTB)
        title="Карта пользователя"
        person=intent.getSerializableExtra("person") as Person
        nameTV.text=person?.name
        surnameTV.text=person?.surname
        phoneTV.text=person?.phone
        val data=person?.data
        val image: Uri? = Uri.parse(person?.photo)
        photoIV.setImageURI(image)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val birthDate = LocalDate.parse(data, formatter)
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)
        val age = period.years
        val nextBirthday = birthDate.withYear(currentDate.year)
        val nextBirthdayAdjusted = if (nextBirthday.isBefore(currentDate)) {
            nextBirthday.withYear(currentDate.year + 1)
        } else {
            nextBirthday
        }
        val monthsUntilNextBirthday = Period.between(currentDate, nextBirthdayAdjusted).months
        val daysUntilNextBirthday = Period.between(currentDate, nextBirthdayAdjusted).days
        dataTV.text="$age лет, $monthsUntilNextBirthday месяцев и $daysUntilNextBirthday дней до дня рождения"

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitApp -> {
                finishAffinity()
                Toast.makeText(this,"Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}