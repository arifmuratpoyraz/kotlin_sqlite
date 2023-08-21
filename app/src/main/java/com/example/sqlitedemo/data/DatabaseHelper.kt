package com.example.sqlitedemo.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "my_database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS person (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT, total TEXT);"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS person")
        onCreate(db)
    }

    fun insertData(name: String, country: String, total: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("country", country)
            put("total", total)
        }
        db.insert("person", null, contentValues)
        db.close()
    }

    fun getAllPeople(): List<Person> {
        val peopleList = mutableListOf<Person>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM person", null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val country = getString(getColumnIndexOrThrow("country"))
                val total = getString(getColumnIndexOrThrow("total"))
                val person = Person(id, name, country, total)
                peopleList.add(person)
            }
            close()
        }
        return peopleList
    }

    fun deletePerson(personId: Long): Int {
        val db = writableDatabase
        val result = db.delete("person", "id=?", arrayOf(personId.toString()))
        db.close()
        return result
    }

    fun updatePerson(id: Long, name: String, country: String, total: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("country", country)
        contentValues.put("total", total)
        val result = db.update("person", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return result != -1
    }

    @SuppressLint("Range")
    fun getPerson(id: Long): Person? {
        val db = this.readableDatabase
        val cursor = db.query(
            "person",
            arrayOf("id", "name", "country", "total"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var person: Person? = null
        if (cursor.moveToFirst()) {
            val personId = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val country = cursor.getString(cursor.getColumnIndex("country"))
            val total = cursor.getString(cursor.getColumnIndex("total"))
            person = Person(personId.toLong(), name, country, total)
        }
        cursor.close()
        db.close()
        return person
    }
}