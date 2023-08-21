package com.example.sqlitedemo.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sqlitedemo.R
import com.example.sqlitedemo.data.DatabaseHelper
import com.example.sqlitedemo.data.Person

class UpdateActivity : AppCompatActivity() {

    private lateinit var buttonSave2: Button
    private lateinit var clientNameEditText2: EditText
    private lateinit var countryEditText2: EditText
    private lateinit var totalEditText2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        buttonSave2 = findViewById(R.id.buttonSave2)
        clientNameEditText2 = findViewById(R.id.clientNameEditText2)
        countryEditText2 = findViewById(R.id.countryEditText2)
        totalEditText2 = findViewById(R.id.totalEditText2)

        val personId = intent.getLongExtra("personId", -1)
        val databaseHelper = DatabaseHelper(this)
        val person: Person? = databaseHelper.getPerson(personId)

        if (person != null) {
            clientNameEditText2.setText(person.name)
            countryEditText2.setText(person.country)
            totalEditText2.setText(person.total)
        }

        buttonSave2.setOnClickListener {
            val updatedName = clientNameEditText2.text.toString()
            val updatedCountry = countryEditText2.text.toString()
            val updatedTotal = totalEditText2.text.toString()

            if (person != null) {
                val databaseHelper = DatabaseHelper(this)
                databaseHelper.updatePerson(person.id, updatedName, updatedCountry, updatedTotal)
                val resultIntent = Intent()
                resultIntent.putExtra("personId", person.id)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            Toast.makeText(this,"Person Updated", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}