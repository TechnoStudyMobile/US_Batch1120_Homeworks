package com.example.orderappkotlinversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        text_view_amount.text = orderViewModel.count.toString()
        text_view_receipt.text = orderViewModel.text_view_receipt_label

        button_increase.setOnClickListener {
            orderViewModel.count++
            text_view_amount.text = orderViewModel.count.toString()
        }

        button_decrease.setOnClickListener {
            if (orderViewModel.count > 0) {
                orderViewModel.count--
                text_view_amount.text = orderViewModel.count.toString()
            }
        }

        check_box_whipped_cream.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            Log.d("Myapp", "Status of checkbox: $isChecked")
            orderViewModel.whippedCreamChecked = isChecked
        }

        check_box_chocolate.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            Log.d("Myapp", "Status of checkbox: $isChecked")
            orderViewModel.chocolateChecked = isChecked
        }

        button_order.setOnClickListener {
            orderViewModel.edit_text_name_label = edit_text_name.text.toString()
            text_view_receipt.text = orderViewModel.set_receipt_text()
        }
    }
}