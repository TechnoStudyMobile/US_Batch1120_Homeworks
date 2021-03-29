package com.crnkic.weatherapp.view.forecastdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import com.crnkic.weatherapp.util.DrawableUtil.getImageId
import com.crnkic.weatherapp.view.forecastlist.ForecastViewModel
import com.crnkic.weatherapp.view.forecastlist.ForecastViewModelFactory
import kotlinx.android.synthetic.main.forcast_details_fragment.*

class ForecastDetailsFragment : Fragment() {
    private lateinit var forecastViewModel: ForecastViewModel

    private val args : ForecastDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel = ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.getSavedForecastContainer()

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

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {forecastContainerResult ->

                when(forecastContainerResult) {
                    is ForecastContainerResult.Failure -> TODO()
                    ForecastContainerResult.IsLoading -> TODO()
                    is ForecastContainerResult.Success -> {
                        image_view_cloud_detailed_fragment.setImageResource(R.drawable::class.java.getImageId(forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.weather?.icon))

                        text_view_day_detailed_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.valid_date.toString()
                        text_view_temperature_detailed_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.high_temp?.toInt().toString()+"°"
                        text_view_temperature_feels_like_detailed_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.low_temp?.toInt().toString()+"°"
                        humidity_details_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.pres.toString() + " hPa"
                        pressure_details_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.pres.toString() + " hPa"
                        wind_detailes_fragment.text = forecastContainerResult.forecastContainer.forecastList.getOrNull(args.position)?.wind_spd.toString() + " km/h SE"
                    }
                }
//                for.text = it.forecastList.getOrNull(args.position).toString()
            }
        })

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
                //TODO: share forecast details as a text
            }
        }
        return super.onOptionsItemSelected(item)
    }
}