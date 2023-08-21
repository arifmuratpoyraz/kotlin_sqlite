package com.example.sqlitedemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.ui.adapters.PersonAdapter
import com.example.sqlitedemo.R
import com.example.sqlitedemo.data.DatabaseHelper
import com.example.sqlitedemo.data.Person


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), PersonAdapter.ItemClickListener {

    private lateinit var recyclerView : RecyclerView
    private val databaseHelper = DatabaseHelper(this)
    private lateinit var adapter: PersonAdapter
    private  lateinit var sharedCardView: CardView

    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        finishAffinity()
    }



    companion object {
        private const val UPDATE_REQUEST_CODE = 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_add -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val peopleList = databaseHelper.getAllPeople()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter = PersonAdapter(peopleList, this)
        recyclerView.adapter = adapter
        sharedCardView = findViewById(R.id.sharedCardView)

        sharedCardView.setOnClickListener{
            val intent = Intent(this, SharedActivity::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onMenuButtonClick(view: View, person: Person) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.items)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.update_id -> {
                    val intent = Intent(this, UpdateActivity::class.java)
                    intent.putExtra("personId", person.id)
                    startActivityForResult(intent, UPDATE_REQUEST_CODE)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete_id -> {
                    databaseHelper.deletePerson(person.id)
                    adapter.notifyDataSetChanged()
                    onResume()
                    Toast.makeText(this,"Person Deleted", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true

                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    override fun onResume() {
        super.onResume()
        val peopleList = databaseHelper.getAllPeople()
        adapter = PersonAdapter(peopleList, this)
        recyclerView.adapter = adapter
    }


}
