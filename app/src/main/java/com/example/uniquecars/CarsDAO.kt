package com.example.uniquecars

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CarsDAO(context: Context) : SQLiteOpenHelper(context, "UniqCar", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS cars (id INTEGER PRIMARY KEY AUTOINCREMENT,carname TEXT, carmodel TEXT,caryear TEXT,image BLOB)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS cars")
        onCreate(p0)
    }

    fun insertDB(name: String, model: String, year: String, carimage: ByteArray) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("carname", name)
        values.put("carmodel", model)
        values.put("caryear", year)
        values.put("image", carimage)
        db.insertOrThrow("cars", null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getAllDB(): ArrayList<Cars> {
        val arrayList = ArrayList<Cars>()
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM cars", null)
        while (cursor.moveToNext()) {
            val cars = Cars(
                cursor.getString(cursor.getColumnIndexOrThrow("carname")),
                cursor.getString(cursor.getColumnIndexOrThrow("carmodel")),
                cursor.getString(cursor.getColumnIndexOrThrow("caryear")),
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getBlob(cursor.getColumnIndexOrThrow("image"))
            )
            arrayList.add(cars)
        }
        return arrayList
    }

    fun deleteDB(id: Int) {
        val db = writableDatabase
        db.delete("cars", "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun update(name: String, model: String, year: String, id: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("carname", name)
        values.put("carmodel", model)
        values.put("caryear", year)
        db.update("cars", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
}