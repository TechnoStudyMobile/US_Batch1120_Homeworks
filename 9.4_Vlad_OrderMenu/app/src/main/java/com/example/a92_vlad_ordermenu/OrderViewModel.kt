package com.example.a92_vlad_ordermenu

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    val isCholateLiveData = MutableLiveData<Boolean>()
    val isCreamLiveData = MutableLiveData<Boolean>()
    var totalCountLiveData = MutableLiveData<Int>()
    var nameLiveData = MutableLiveData<String>()
    var costAddWhippedCream = 1.50
    var costAddChocolate = 2.75
    var totalCost = 0.0
    var costBasic: Double = 5.00

    fun increaseQuantity() {
        totalCountLiveData.value = (totalCountLiveData.value)?.plus(1)

    }

    fun decreaseQuantity() {
        if (totalCountLiveData.value!! > 0) {
            totalCountLiveData.value = (totalCountLiveData.value)?.minus(1)
        }

    }

    fun makeOrder(name: String): String {
        totalCountLiveData.value = 0
        isCreamLiveData.value = false
        isCholateLiveData.value = false
        nameLiveData.value = ""
        return """
        
                    Add whipped cream? ${isCreamLiveData.value}
                    Add chocolate? ${isCholateLiveData.value}
                    Quantity: ${totalCountLiveData.value}
                    Total is: $${calculateTotal()}
                """.trimIndent()
    }

    fun calculateTotal(): Double {
        var tempcream = 0.0
        var tempchocolate = 0.0
        if (isCreamLiveData.value == true) {
            tempcream = costAddWhippedCream * totalCountLiveData.value!!
        }
        if (isCholateLiveData.value == true) {
            tempchocolate = costAddChocolate * totalCountLiveData.value!!
        }
        totalCost = totalCountLiveData.value!! * costBasic + tempcream + tempchocolate
        return totalCost
    }

    fun chocolateChecked() {
        isCholateLiveData.value = true

    }

    fun whippedCreamChecked() {
        isCreamLiveData.value = true

    }

    fun getName(s: String) {
        nameLiveData.value = s

    }


}