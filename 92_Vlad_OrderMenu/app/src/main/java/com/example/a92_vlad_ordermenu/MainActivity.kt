package com.example.a92_vlad_ordermenu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.a92_vlad_ordermenu.MenuViewModel.Companion.chocolate
import com.example.a92_vlad_ordermenu.MenuViewModel.Companion.whippedCream
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
            text_view_reciept.text =edit_text_name.text.toString()+"\n"+menuViewModel.makeOreder()
        }

        checkbox_whipped_cream.setOnCheckedChangeListener { buttonView, isChecked ->
            whippedCream = isChecked
        }

        checkbox_chocolate.setOnCheckedChangeListener { buttonView, isChecked ->
            chocolate = isChecked
        }

    }


}
