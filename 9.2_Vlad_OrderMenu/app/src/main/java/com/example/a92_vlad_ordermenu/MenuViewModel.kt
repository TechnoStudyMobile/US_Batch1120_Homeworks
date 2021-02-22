package com.example.a92_vlad_ordermenu

import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    val costAddWhippedCream = 1.50
    val costAddChocolate = 2.75
    var totalCost = 0.0
    var count: Int = 0
    val costBasic: Double = 5.00
    var chocolateChecked: Boolean = false
    var whippedCreamChecked: Boolean = false

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

    fun makeOrder(): String {
        return if (count > 0) """
                    Add whipped cream? ${whippedCreamChecked}
                    Add chocolate? ${chocolateChecked}
                    Quantity: $count
                    Total is: $${calculateTotal()}
                """.trimIndent() else "You need to choose coffee first"
    }

    private fun calculateTotal(): Double {
        var tempcream = 0.0
        var tempchocolate = 0.0
        if (whippedCreamChecked) {
            tempcream = costAddWhippedCream * count
        }
        if (chocolateChecked) {
            tempchocolate = costAddChocolate * count
        }
        totalCost = count * costBasic + tempcream + tempchocolate
        return totalCost
    }
}