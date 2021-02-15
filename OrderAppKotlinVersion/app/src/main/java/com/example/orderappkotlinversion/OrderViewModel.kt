package com.example.orderappkotlinversion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private var count: Int = 0
    private var edit_text_name_label: String = ""
    private var whippedCreamChecked: Boolean = false
    private var chocolateChecked: Boolean = false

    private val _countLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val countLiveData: LiveData<Int>
        get() = _countLiveData

    private val _textViewReceiptLiveData: MutableLiveData<String> = MutableLiveData()
    val textViewReceiptLiveData: LiveData<String>
        get() = _textViewReceiptLiveData

    fun onClickButtonIncrease() {
        count++
        _countLiveData.value = count
        updateReceiptText()
    }

    fun onClickButtonDecrease() {
        if (count > 0) {
            count--
        }
        _countLiveData.value = count
        updateReceiptText()
    }

    fun onClickChocolateCheckBox(isCheched: Boolean) {
        chocolateChecked = isCheched
        updateReceiptText()
    }

    fun onWhippedCreamCheckBox(isCheched: Boolean) {
        whippedCreamChecked = isCheched
        updateReceiptText()
    }

    fun onEditTextNameChanged(s: String) {
        edit_text_name_label = s
        updateReceiptText()
    }

    private fun calculateTotal(): Int {
        var total = 0
        if (whippedCreamChecked) total += 10
        if (chocolateChecked) total += 5
        total *= count
        return total
    }

    fun updateReceiptText() {
        _textViewReceiptLiveData.value = "ORDER SUMMARY\n" +
                "Person: $edit_text_name_label\n" +
                "Add wipped cream? $whippedCreamChecked\n" +
                "Add chocolate? $chocolateChecked\n" +
                "Quantity: $count\n" +
                "Total: \$${calculateTotal()}\n"
    }
}