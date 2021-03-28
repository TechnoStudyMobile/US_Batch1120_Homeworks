package com.kryvovyaz.a96_weatherapplication.screen.forecastdetails

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
import com.kryvovyaz.a96_weatherapplication.ForecastViewModelFactory
import com.kryvovyaz.a96_weatherapplication.databinding.FragmentForecastDetailsBinding
import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerResult
import com.kryvovyaz.a96_weatherapplication.App
import com.kryvovyaz.a96_weatherapplication.util.TextUtil.capitalizeWords
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.DrawableUtil.getImageId
import com.kryvovyaz.a96_weatherapplication.util.TimeUtil.convertTime

class ForecastDetailsFragment : Fragment() {
    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var forecastViewModel: ForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel = ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
       // forecastViewModel.getForecastContainer(App.prefs!!.icCelsius, App.prefs!!.days)
        forecastViewModel.getSavedForecastContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        return binding.root
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

    private fun getForecastDetails() {
        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Failure -> {
                    }
                    ForecastContainerResult.IsLoading -> {
                    }
                    is ForecastContainerResult.Success -> {
                        forecastContainerResult.forecastContainer.forecastList
                            .getOrNull(args.position)?.weather?.icon?.let { it1 ->
                                R.drawable::class.java.getImageId(
                                    it1
                                )
                            }?.let { it2 ->
                                binding.iconDetails.setImageResource(
                                    it2
                                )
                            }
                        binding.humidity.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.humidityAverage.toString().plus(
                                    getString(
                                        R.string.space
                                    )
                                ).plus(
                                    getString(
                                        R.string.percent
                                    )
                                )
                        binding.pressure.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.pressureAverage?.toInt().toString()
                                .plus(
                                    getString(
                                        R.string.space
                                    )
                                )
                                .plus(
                                    if (App.prefs!!.icCelsius)
                                        getString(R.string.pesure_m) else getString(
                                        R.string.presure_i
                                    )
                                )
                       binding.wind.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.wind_spd?.toInt()
                                .toString().plus(
                                    getString(
                                        R.string.space
                                    )
                                )
                                .plus(
                                    if (App.prefs!!.icCelsius)
                                        getString(R.string.wind_speed_m) else getString(
                                        R.string.wind_speed_i
                                    )
                                ).plus(
                                    getString(
                                        R.string.space
                                    )
                                )
                                .plus(
                                    forecastContainerResult.forecastContainer.forecastList
                                        .getOrNull(args.position)?.abdreviatedWindDirection
                                )
                        binding.forecastDetails.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.weather?.description
                        binding.dateDetails.text = forecastContainerResult.forecastContainer.forecastList
                            .getOrNull(args.position)?.datetime?.let { it1 ->
                                context?.let { it2 ->
                                    formatDate(
                                        it1, args.position, it2
                                    )
                                }
                            }?.let { it3 -> capitalizeWords(it3) }
                        binding.tempHighDetails.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.high_temp?.toInt().toString()
                                .plus(context?.getString(R.string.degree_character))
                        binding.tempLowDetails.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.low_temp?.toInt().toString()
                                .plus(context?.getString(R.string.degree_character))
                        binding.sunrise.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.sunrise_time_unix_timestamps?.let { it1 ->
                                    convertTime(
                                        it1
                                    )
                                }
                        binding.sunset.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.sunset_time_unix_timestamps?.let { it1 ->
                                    convertTime(
                                        it1
                                    )
                                }
                        binding.precipitation.text =
                            forecastContainerResult.forecastContainer.forecastList
                                .getOrNull(args.position)?.probability?.toString().plus(
                                    getString(
                                        R.string.percent
                                    )
                                )
                    }
                }

            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        App.prefs!!.lattitude?.let {
            App.prefs!!.longtitude?.let { it1 ->
                forecastViewModel.fetchForecastContainer(App.prefs!!.icCelsius, App.prefs!!.days,
                    it, it1
                )
            }
        }
        getForecastDetails()
    }
}