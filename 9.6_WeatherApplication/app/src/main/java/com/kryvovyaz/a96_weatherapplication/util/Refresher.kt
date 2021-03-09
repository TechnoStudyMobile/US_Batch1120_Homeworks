package com.kryvovyaz.a96_weatherapplication.util

import android.app.Activity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel

interface Refresher:SwipeRefreshLayout.OnRefreshListener {
   fun onRefresh(activity: Activity,viewModel: ForecastViewModel){
        val isCelsius = Prefs.retrieveIsCelsiusSetting(activity)
        val days = Prefs.loadDaysSelected(activity)
        viewModel.getForecastContainer(isCelsius, days)
        SwipeRefreshLayout.GONE
    }
}