package com.example.orderappkotlinversion

import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    var whippedCreamChecked: Boolean = false
    var chocolateChecked: Boolean = false
    var count: Int = 0
    var edit_text_name_label: String = ""
    var text_view_receipt_label: String = ""

    fun calculateTotal(): Int {
        var total: Int = 0
        if (whippedCreamChecked) total += 10
        if (chocolateChecked) total += 5
        total *= count
        return total
    }

    fun set_receipt_text(): String {
        text_view_receipt_label = "ORDER SUMMARY\n" +
                "Person: $edit_text_name_label\n" +
                "Add wipped cream? $whippedCreamChecked\n" +
                "Add chocolate? $chocolateChecked\n" +
                "Quantity: $count\n" +
                "Total: \$${calculateTotal()}\n" +
                "Thank you!!!"
        return text_view_receipt_label
    }
}