package com.example.a13_order_app_kotlin_viewmodel

import androidx.lifecycle.ViewModel

class ItemViewModel: ViewModel() {

    private var item = 0

    fun increaseBy(value: Int) {
        item += value
    }

    fun decreaseBy(value: Int) {
        if (item > 0) {
            item -= value
        }
    }

    fun retrieveScore(): String {
        return item.toString()
    }
}