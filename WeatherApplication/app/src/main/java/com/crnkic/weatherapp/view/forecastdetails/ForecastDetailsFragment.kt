package com.crnkic.weatherapp.view.forecastdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.forecastResponse.Forecast
import com.crnkic.weatherapp.util.DrawableUtil.getImageId
import com.crnkic.weatherapp.util.Prefs
import com.crnkic.weatherapp.view.forecastlist.WeatherViewModel
import kotlinx.android.synthetic.main.forcast_details_fragment.*

class ForecastDetailsFragment : Fragment() {
    private lateinit var forcastViewModel: WeatherViewModel

    private val args : ForecastDetailsFragmentArgs by navArgs()

    var listOfItems: Forecast? = null
    private var position = 0

    companion object {
        fun newInstance() = ForecastDetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        forcastViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

//        arguments?.let {
//            listOfItems = it.getParcelable(KEY_DAILY_FORECAST_DETAILS)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forcast_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forcastViewModel.listOfItemsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
//                for.text = it.forecastList.getOrNull(args.position).toString()
                image_view_cloud_detailed_fragment.setImageResource(R.drawable::class.java.getImageId(it.forecastList.getOrNull(args.position)?.weather?.icon))

                text_view_day_detailed_fragment.text = it.forecastList.getOrNull(args.position)?.valid_date.toString()
                text_view_temperature_detailed_fragment.text = it.forecastList.getOrNull(args.position)?.high_temp?.toInt().toString()+"°"
                text_view_temperature_feels_like_detailed_fragment.text = it.forecastList.getOrNull(args.position)?.low_temp?.toInt().toString()+"°"
                humidity_details_fragment.text = it.forecastList.getOrNull(args.position)?.pres.toString() + " hPa"
                pressure_details_fragment.text = it.forecastList.getOrNull(args.position)?.pres.toString() + " hPa"
                wind_detailes_fragment.text = it.forecastList.getOrNull(args.position)?.wind_spd.toString() + " km/h SE"
            }

        })

        activity?.let {
            val value = Prefs.retrieveIsCelsiusSetting(it)
            Toast.makeText(it, value.toString(), Toast.LENGTH_SHORT).show()
        }

//        forecast_details.text = listOfItems.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                val direction = ForecastDetailsFragmentDirections.actionForcastDetailsFragmentToSettingFragment()
                findNavController().navigate(direction)
            }

            R.id.share -> {
                //TODO: share forcast details as a text
            }
        }
        return super.onOptionsItemSelected(item)
    }
}