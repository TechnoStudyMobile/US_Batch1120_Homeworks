package com.samil.app.theweather.screen.forecastdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.samil.app.theweather.R
import com.samil.app.theweather.model.Forecast
import com.samil.app.theweather.screen.forecastlist.ForecastViewModel
import com.samil.app.theweather.utils.Prefs
import kotlinx.android.synthetic.main.fragment_forecast_details.*

/**
 * @author Samil Balci
 */

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var forecastViewModel: ForecastViewModel

    private var forecast: Forecast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        forecast_details_text_view.text = forecastViewModel
//            .forecastListLiveData.value?.forecastList?.get(args.position)?.toString()
        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                forecast_details_text_view.text = it.forecastList.getOrNull(args.position)?.toString()
            }
        })
        activity?.let {
            val value = Prefs.retrieveIsCelsiusSetting(it)
            Toast.makeText(it, value.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}