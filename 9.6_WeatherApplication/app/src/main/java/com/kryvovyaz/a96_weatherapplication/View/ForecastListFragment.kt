package com.kryvovyaz.a96_weatherapplication.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel.model.Json4Kotlin_Base
import com.kryvovyaz.a96_weatherapplication.R
import kotlinx.android.synthetic.main.recycler.*

class ForecastListFragment(val forecastLlistData: Json4Kotlin_Base) : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WeatherAdapter(forecastLlistData)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.weatherRecycler)
        weatherRecycler.layoutManager = layoutManager
        weatherRecycler.adapter = adapter
    }


//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @SuppressLint("SimpleDateFormat")
//    fun getDays(days: Int): String {
//        var formatter = DateTimeFormatter.ofPattern("EE, MMM yy")
//        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @SuppressLint("SimpleDateFormat")
//    fun getDay(days: Int): String {
//        var formatter = DateTimeFormatter.ofPattern("EEEE")
//        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
//
//    }

//    These methods should be in ViewModel or seperate Util class. You can create an Util class using singleton pattern (using object keyword in Kotlin) and put all these methods there.
//    In seperate file
//    object DateUtil {
//        @RequiresApi(Build.VERSION_CODES.O)
//        @SuppressLint("SimpleDateFormat")
//        fun getDay(days: Int): String {
//            var formatter = DateTimeFormatter.ofPattern("EEEE")
//            return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
//        }

}