package com.kryvovyaz.a96_weatherapplication

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private val activity: Activity) {

    lateinit var dialog: AlertDialog
    fun startLoadingAnimation() {
        val inflater = activity.layoutInflater
        dialog = AlertDialog.Builder(activity)
            .setView(inflater.inflate(R.layout.dialog_loading, null)).setCancelable(false).create()
        dialog.show()
    }
    fun dismissDialog(){
        dialog.dismiss()
    }

}