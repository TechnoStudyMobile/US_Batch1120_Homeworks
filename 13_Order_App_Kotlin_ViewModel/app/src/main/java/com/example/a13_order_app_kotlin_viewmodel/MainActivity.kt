package com.example.a13_order_app_kotlin_viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel

    private var whipedcream = false
    private var chocolatechecked = false

    lateinit var itemCount: TextView
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var order: Button
    lateinit var cream: CheckBox
    lateinit var chocolate: CheckBox
    lateinit var receipt: TextView
    lateinit var toppings: TextView
    lateinit var editText: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemCount = findViewById(R.id.amount)
        button1 = findViewById(R.id.plus)
        button2 = findViewById(R.id.minus)
        order = findViewById(R.id.order)
        cream = findViewById(R.id.whipped_cream)
        chocolate = findViewById(R.id.chocolate)
        receipt = findViewById(R.id.reciept)
        toppings = findViewById(R.id.toppings)
        editText = findViewById(R.id.name_text)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemCount.text = itemViewModel.retrieveScore()

        toppings.setOnClickListener { }

        button1.setOnClickListener {
            itemViewModel.increaseBy(1)
            itemCount.text = itemViewModel.retrieveScore()
        }

        button2.setOnClickListener {
            itemViewModel.decreaseBy(1)
            itemCount.text = itemViewModel.retrieveScore()
        }

        cream.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("Myapp", "Status of checkbox $isChecked")
            whipedcream = isChecked
        }

        chocolate.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("Myapp", "Status of checkbox $isChecked")
            chocolatechecked = isChecked
        }

        order.setOnClickListener {
            var total: Int = 0
            if (whipedcream) {
                total += 10
            }
            if (chocolatechecked) {
                total += 5
            }
            total *= itemViewModel.retrieveScore().toInt()

            receipt.text = """
                ORDER SUMMARY
                Person: ${editText.text}
                Add wipped cream? $whipedcream
                Add chocolate? $chocolatechecked
                Quantity: ${itemCount.text}
                Total$total$
                Thank you!!!
                """.trimIndent()
        }
    }
}