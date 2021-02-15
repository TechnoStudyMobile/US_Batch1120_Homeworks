package com.example.saturdayprojectordermenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        checkBox_whipped_cream.setOnCheckedChangeListener { buttonView, isChecked ->
            mainActivityViewModel.whippedCreamChecked()
        }

        checkBox_chocolate.setOnCheckedChangeListener { buttonView, isChecked ->
            mainActivityViewModel.chocolateChecked()
        }

        button_minus.setOnClickListener {
            textView_numberOfOrders.text = mainActivityViewModel.decreaseCount(it)
        }

        button_plus.setOnClickListener {
            textView_numberOfOrders.text = mainActivityViewModel.increaseCount(it)
        }

        button_order.setOnClickListener {
            mainActivityViewModel.orderTotal()
            textView_receipt.text = mainActivityViewModel.orderDetails(editText_nameOfOrder)
        }
    }
}