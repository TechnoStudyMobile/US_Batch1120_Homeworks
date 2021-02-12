package com.example.a92_vlad_ordermenu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_order.*


class OrderActivity : AppCompatActivity() {

    private lateinit var orderViewModel: OrderViewModel;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        orderViewModel.totalCountLiveData.value = 0
        text_view_reciept.text = orderViewModel.makeOrder("Vlad")

        edit_text_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                orderViewModel.getName(edit_text_name.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        button_plus.setOnClickListener {
            orderViewModel.increaseQuantity()
        }

        button_minus.setOnClickListener {
            orderViewModel.decreaseQuantity()
        }

        button_order.setOnClickListener {

            showDialog()
        }

        checkbox_whipped_cream.setOnCheckedChangeListener { buttonView, isChecked ->
            orderViewModel.isCreamLiveData.value = isChecked
        }

        checkbox_chocolate.setOnCheckedChangeListener { buttonView, isChecked ->
            orderViewModel.isCholateLiveData.value=isChecked
        }

        orderViewModel.isCholateLiveData.observe(this, Observer { isChocolateChecked ->
            printDetails()
        })

        orderViewModel.isCreamLiveData.observe(this, Observer { isCreamChecked ->
            printDetails()
        })

        orderViewModel.totalCountLiveData.observe(this, Observer<Int> { totalCount ->
            printDetails()
        })

        orderViewModel.nameLiveData.observe(this, Observer<String> { name ->
            printDetails()
        })
    }

    @SuppressLint("SetTextI18n")
    fun printDetails() {
        text_view_reciept.text = """
                    ${orderViewModel.nameLiveData.value}
                    Add whipped cream? ${orderViewModel.isCholateLiveData.value}
                    Add chocolate? ${orderViewModel.isCreamLiveData.value}
                    Quantity: ${orderViewModel.totalCountLiveData.value}
                    Total is: $${orderViewModel.calculateTotal()}
                """.trimIndent()

        text_view_amount.text = orderViewModel.totalCountLiveData.value.toString()
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to checkout?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                //text_view_reciept.text = orderViewModel.makeOrder(edit_text_name.text.toString())
                text_view_reciept.text = "Thank you,you order is placed"
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
