package com.example.saturdayprojectordermenu

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private var priceOfWhippedCream = 4
    private var priceOfChocolate = 5
    private var orderTotal: Int = 0
    private var count = 0
    private var whippedCream = "No"
    private var chocolate = "No"

    fun whippedCreamChecked() {
        whippedCream = "Yes"
        orderTotal += priceOfWhippedCream
    }

    fun chocolateChecked() {
        chocolate = "Yes"
        orderTotal += priceOfChocolate
    }

    fun orderTotal(): Int {
        return orderTotal * count
    }

    fun orderDetails(editText_nameOfOrder : EditText): String {
        return if((whippedCream == "No" && chocolate == "No") || count<1) {
            "Please choose >TOPPINGS< and add >QUANTITY<"
        } else {
            """
            ORDER DETAILS
            Name of order: ${editText_nameOfOrder.text}
            Add whipped cream? $whippedCream
            Add chocolate? $chocolate
            Quantity: $count
            Total: ${orderTotal()}
            Thank You!
             """.trimIndent()
        }
    }

    fun increaseCount(view: View): String {
        count++
        return count.toString()
    }

    fun decreaseCount(view: View): String {
        return if (count > 0) {
            count--
            count.toString()
        } else {
            "0"
        }
    }

}