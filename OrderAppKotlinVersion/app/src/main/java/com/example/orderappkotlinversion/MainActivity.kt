package com.example.orderappkotlinversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        button_increase.setOnClickListener {
            orderViewModel.onClickButtonIncrease()
        }

        button_decrease.setOnClickListener {
            orderViewModel.onClickButtonDecrease()
        }

        check_box_whipped_cream.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            orderViewModel.onWhippedCreamCheckBox(isChecked)
        }

        check_box_chocolate.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            orderViewModel.onClickChocolateCheckBox(isChecked)
        }

        button_order.setOnClickListener {
            Toast.makeText(this, "Order received. Thank you!", Toast.LENGTH_SHORT).show()
        }

        orderViewModel.textViewReceiptLiveData.observe(this, Observer {
            text_view_receipt.text = it
        })

        orderViewModel.countLiveData.observe(this, Observer {
            text_view_amount.text = it.toString()
        })

        edit_text_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                orderViewModel.onEditTextNameChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}