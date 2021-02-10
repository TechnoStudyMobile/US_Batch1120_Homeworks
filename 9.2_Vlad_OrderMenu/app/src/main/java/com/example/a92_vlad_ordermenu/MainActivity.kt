package com.example.a92_vlad_ordermenu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var menuViewModel: MenuViewModel;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        button_plus.setOnClickListener { text_view_amount.text = menuViewModel.increaseQuantity() }

        button_minus.setOnClickListener { text_view_amount.text = menuViewModel.decreaseQuantity() }

        button_order.setOnClickListener {
            text_view_reciept.text =
                edit_text_name.text.toString() + "\n" + menuViewModel.makeOrder()
        }

        checkbox_whipped_cream.setOnCheckedChangeListener { buttonView, isChecked ->
            menuViewModel.chocolateChecked()
        }

        checkbox_chocolate.setOnCheckedChangeListener { buttonView, isChecked ->
            menuViewModel.whippedCreamChecked()
        }
    }
}
