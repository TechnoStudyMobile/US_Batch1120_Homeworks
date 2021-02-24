package com.kryvovyaz.a96_weatherapplication.screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.kryvovyaz.a96_weatherapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)}

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.search -> Toast.makeText(
                    this,
                    "Search location option to be implemented",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.settings -> findNavController(R.id.fragment_container)
                    .navigate(R.id.settingsFragment)
            }
            return super.onOptionsItemSelected(item)
        }
    }