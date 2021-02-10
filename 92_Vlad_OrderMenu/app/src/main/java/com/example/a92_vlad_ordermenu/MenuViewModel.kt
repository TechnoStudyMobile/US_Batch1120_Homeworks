package com.example.a92_vlad_ordermenu

import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    var costAddWhippedCream = 1.50
    var costAddChocolate = 2.75
    var totalCost = 0.0
    var count: Int = 0

    var costBasic: Double = 5.00
companion object{
    var whippedCream: Boolean = false
    var chocolate: Boolean = false
    }

    fun increaseQuantity(): String {
        count++
        return count.toString()
    }

    fun decreaseQuantity(): String {
        if (count > 0) {
            count--
        }
        return count.toString()
    }

     fun makeOreder(): String {

        return if (count > 0) """
                    Add whipped cream? $whippedCream
                    Add chocolate? $chocolate
                    Quantity: $count
                    Total is: $${calculateTotal()}
                """.trimIndent() else "You need to choose coffee first"
    }

     fun calculateTotal(): Double {
        var tempcream = 0.0
        var tempchocolate = 0.0
        if (whippedCream) {
            tempcream = costAddWhippedCream * count
        }
        if (chocolate) {
            tempchocolate = costAddChocolate * count
        }
        totalCost = count * costBasic + tempcream + tempchocolate
        return totalCost
    }
}