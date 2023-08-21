package com.example.sqlitedemo.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sqlitedemo.R

class SharedActivity : AppCompatActivity() {

    private lateinit var sharedSaveButton : Button
    private lateinit var sharedDeleteButton: Button
    private lateinit var sharedSavedTextView : TextView
    private lateinit var sharedEditText : EditText
    private lateinit var sharedPreferences : SharedPreferences
    var savedData : String? = null


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)

        sharedSaveButton = findViewById(R.id.sharedSaveButton)
        sharedDeleteButton = findViewById(R.id.sharedDeleteButton)
        sharedEditText = findViewById(R.id.sharedEditText)
        sharedSavedTextView = findViewById(R.id.sharedSavedTextView)
        sharedPreferences = this.getSharedPreferences("com.example.sqlitedemo.ui", MODE_PRIVATE)
        savedData = sharedPreferences.getString("savedData","Saved Text")

        if (savedData != null){
            sharedSavedTextView.text = savedData
        }


        sharedSaveButton.setOnClickListener {
            val data = sharedEditText.text.toString()
            if (data == ""){
                Toast.makeText(this,"Please Enter A Value",Toast.LENGTH_SHORT).show()
            }else{
            sharedPreferences.edit().putString("savedData",data).apply()
                sharedSavedTextView.text = data
            }

        }

        sharedDeleteButton.setOnClickListener{

            savedData = sharedPreferences.getString("savedData","Saved Text")

            if (savedData != null){
                sharedSavedTextView.text = "Saved Text"
                sharedPreferences.edit().remove("savedData").apply()
            }
        }



    }
}