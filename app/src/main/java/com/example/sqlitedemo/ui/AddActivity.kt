package com.example.sqlitedemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sqlitedemo.R
import com.example.sqlitedemo.data.DatabaseHelper

class AddActivity : AppCompatActivity() {

    private lateinit var buttonSave: Button
    private lateinit var clientNameEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var totalEditText: EditText
    private val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        buttonSave = findViewById(R.id.buttonSave)
        clientNameEditText = findViewById(R.id.clientNameEditText)
        countryEditText = findViewById(R.id.countryEditText)
        totalEditText = findViewById(R.id.totalEditText)

        buttonSave.setOnClickListener {
            val name = clientNameEditText.text.toString()
            val country = countryEditText.text.toString()
            val total = totalEditText.text.toString()
            databaseHelper.insertData(name, country, total)
            Toast.makeText(this,"Person Added", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}