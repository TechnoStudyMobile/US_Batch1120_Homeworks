package com.kryvovyaz.a96_weatherapplication.screen.forecastdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Data
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.screen.forecastlist.ForecastListFragment
import com.kryvovyaz.a96_weatherapplication.screen.forecastlist.ForecastViewModel
import com.kryvovyaz.a96_weatherapplication.util.DateUtil
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.KEY_DAILY_FORECAST_DETAILS
import kotlinx.android.synthetic.main.fragment_forecast_details.*

class ForecastDetailsFragment : Fragment() {
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var forecastViewModel: ForecastViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                humidity.text =
                    it.data.getOrNull(args.position)?.humidityAverage.toString().plus(" %")
                presure.text = it.data.getOrNull(args.position)?.pressureAverage?.toInt().toString()
                    .plus(" hPa")
                wind.text =
                    it.data.getOrNull(args.position)?.wind_spd?.toInt().toString().plus(" km/h ")
                        .plus(it.data.getOrNull(args.position)?.abdreviatedWindDirection)
                forecast_details.text = it.data.getOrNull(args.position)?.weather?.description
                date_details.text =
                    it.data.getOrNull(args.position)?.datetime?.let { it1 ->
                        formatDate(
                            it1, args.position
                        )
                    }

                tempHigh_details.text =
                    it.data.getOrNull(args.position)?.high_temp?.toInt().toString().plus("°")
                tempLow_details.text =
                    it.data.getOrNull(args.position)?.low_temp?.toInt().toString().plus("°")
            }
        })
    }
}
