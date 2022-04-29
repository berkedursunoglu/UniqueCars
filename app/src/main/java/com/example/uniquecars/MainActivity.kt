package com.example.uniquecars

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uniquecars.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var db = CarsDAO(applicationContext)
        val arrayList = db.getAllDB()
        val rv = CarsRV(this,arrayList)
        binding.mainrecyclerview.layoutManager = GridLayoutManager(applicationContext,2)
        binding.mainrecyclerview.adapter = rv
        binding.toolbar.title = "UniqueCars"
        binding.toolbar.subtitle = "Nadir Araba Kütüphaneniz"

        binding.add.setOnClickListener {
            val intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}