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
import com.kryvovyaz.a96_weatherapplication.screen.forecastlist.ForecastViewModel
import com.kryvovyaz.a96_weatherapplication.util.Capitalization.capitalizeWords
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.IS_CELSIUS_SETTING_PREF_KEY
import com.kryvovyaz.a96_weatherapplication.util.Prefs
import kotlinx.android.synthetic.main.fragment_forecast_details.*

class ForecastDetailsFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {
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
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences(IS_CELSIUS_SETTING_PREF_KEY, MODE_PRIVATE)
        sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
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
        forecastViewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                humidity.text =
                    it.data.getOrNull(args.position)?.humidityAverage.toString().plus(
                        getString(
                            R.string.space
                        )
                    ).plus(
                        getString(
                            R.string.percent
                        )
                    )
                presure.text = it.data.getOrNull(args.position)?.pressureAverage?.toInt().toString()
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
                    it.data.getOrNull(args.position)?.wind_spd?.toInt()
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
                        ).plus(getString(
                            R.string.space))
                        .plus(it.data.getOrNull(args.position)?.abdreviatedWindDirection)
                forecast_details.text = it.data.getOrNull(args.position)?.weather?.description
                date_details.text = it.data.getOrNull(args.position)?.datetime?.let { it1 ->
                    context?.let { it2 ->
                        formatDate(
                            it1, args.position, it2
                        )
                    }
                }?.let { it3 -> capitalizeWords(it3) }
                tempHigh_details.text =
                    it.data.getOrNull(args.position)?.high_temp?.toInt().toString()
                        .plus(context?.getString(R.string.degree_character))
                tempLow_details.text =
                    it.data.getOrNull(args.position)?.low_temp?.toInt().toString()
                        .plus(context?.getString(R.string.degree_character))
            }
        })
    }

    //not working currently
    override fun onResume() {
        super.onResume()
        activity?.getPreferences(MODE_PRIVATE)
            ?.registerOnSharedPreferenceChangeListener(this)
        getForecastDetails()
    }

    override fun onPause() {
        super.onPause()
        activity?.getPreferences(MODE_PRIVATE)
            ?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        getForecastDetails()
    }
}