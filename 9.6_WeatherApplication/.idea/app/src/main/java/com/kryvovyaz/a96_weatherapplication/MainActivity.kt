package com.kryvovyaz.a96_weatherapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kryvovyaz.a96_weatherapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
               binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.design_default_color_on_primary));
        initializeToolbar()
//
//        val crashButton = Button(this)
//        crashButton.setText("Crash!")
//        crashButton.setOnClickListener{
//                throw RuntimeException("Test Crash") // Force a crash
//            }
//
//
//        addContentView(
//            crashButton, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        )
    }

    private fun initializeToolbar() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}