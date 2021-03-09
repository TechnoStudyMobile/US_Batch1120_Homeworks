package com.kryvovyaz.a96_weatherapplication.screen.forecastdetails

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel
import com.kryvovyaz.a96_weatherapplication.util.TextUtil.capitalizeWords
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.DrawableUtil.getImageId
import com.kryvovyaz.a96_weatherapplication.util.IS_CELSIUS_SETTING_PREF_KEY
import com.kryvovyaz.a96_weatherapplication.util.Prefs
import com.kryvovyaz.a96_weatherapplication.util.TimeUtil.convertTime
import kotlinx.android.synthetic.main.fragment_forecast_details.*

class ForecastDetailsFragment : Fragment() {
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var forecastViewModel: ForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

        getForecastDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.share -> Toast.makeText(
                context,
                "Share option to be implemented",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> {
                val directions =
                    ForecastDetailsFragmentDirections
                        .actionForecastDetailsFragmentToSettingsFragment()
                findNavController()
                    .navigate(directions)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getForecastDetails() {
        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.forecastList.getOrNull(args.position)?.weather?.icon?.let { it1 ->
                    R.drawable::class.java.getImageId(
                        it1
                    )
                }?.let { it2 ->
                    icon_details.setImageResource(
                        it2
                    )
                }
                humidity.text =
                    it.forecastList.getOrNull(args.position)?.humidityAverage.toString().plus(
                        getString(
                            R.string.space
                        )
                    ).plus(
                        getString(
                            R.string.percent
                        )
                    )
                pressure.text =
                    it.forecastList.getOrNull(args.position)?.pressureAverage?.toInt().toString()
                        .plus(
                            getString(
                                R.string.space
                            )
                        )
                        .plus(
                            if (Prefs.retrieveIsCelsiusSetting(requireActivity()))
                                getString(R.string.pesure_m) else getString(
                                R.string.presure_i
                            )
                        )
                wind.text =
                    it.forecastList.getOrNull(args.position)?.wind_spd?.toInt()
                        .toString().plus(
                            getString(
                                R.string.space
                            )
                        )
                        .plus(
                            if (Prefs.retrieveIsCelsiusSetting(requireActivity()))
                                getString(R.string.wind_speed_m) else getString(
                                R.string.wind_speed_i
                            )
                        ).plus(
                            getString(
                                R.string.space
                            )
                        )
                        .plus(it.forecastList.getOrNull(args.position)?.abdreviatedWindDirection)
                forecast_details.text =
                    it.forecastList.getOrNull(args.position)?.weather?.description
                date_details.text = it.forecastList.getOrNull(args.position)?.datetime?.let { it1 ->
                    context?.let { it2 ->
                        formatDate(
                            it1, args.position, it2
                        )
                    }
                }?.let { it3 -> capitalizeWords(it3) }
                tempHigh_details.text =
                    it.forecastList.getOrNull(args.position)?.high_temp?.toInt().toString()
                        .plus(context?.getString(R.string.degree_character))
                tempLow_details.text =
                    it.forecastList.getOrNull(args.position)?.low_temp?.toInt().toString()
                        .plus(context?.getString(R.string.degree_character))
                sunrise.text =
                    it.forecastList.getOrNull(args.position)?.sunrise_time_unix_timestamps?.let { it1 ->
                        convertTime(
                            it1
                        ).toString()
                    }
                sunset.text =
                    it.forecastList.getOrNull(args.position)?.sunset_time_unix_timestamps?.let { it1 ->
                        convertTime(
                            it1
                        ).toString()
                    }
                precipitation.text =
                    it.forecastList.getOrNull(args.position)?.probability?.toInt().toString().plus(
                        getString(
                            R.string.percent
                        )
                    )

            }
        })
    }

}