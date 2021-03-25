package ustun.muharrem.weatherforecast.screens.forecastdetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.Forecast
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.databinding.FragmentForecastDetailsBinding
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.utilities.DateUtil
import ustun.muharrem.weatherforecast.utilities.DrawableManager.getImageId
import ustun.muharrem.weatherforecast.utilities.SharedPrefs
import java.util.*
import kotlin.math.roundToInt

class ForecastDetailsFragment : Fragment() {
    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.getForecastContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetailsItemLabels()

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Failure -> {
                        // TODO: Show error message
                    }
                    ForecastContainerResult.IsLoading -> {
                        // TODO: Show spinner
                    }
                    is ForecastContainerResult.Success -> {
                        binding.cityName.text = forecastContainerResult.forecastContainer.city_name
                        setDetailsHeaderValues(forecastContainerResult.forecastContainer.data[args.position])
                        setDetailsItemValues(forecastContainerResult.forecastContainer.data[args.position])
                    }
                }
            }
        })
    }

    private fun setDetailsHeaderValues(forecast: Forecast) {
        binding.dateTextView.text = DateUtil.getDateText(forecast.valid_date, 0)
        binding.iconImageView.setImageResource(R.drawable::class.java.getImageId(forecast.weather.icon))
        binding.highTempTextView.text = forecast.high_temp.roundToInt().toString().plus("°")
        binding.lowTempTextView.text = forecast.low_temp.roundToInt().toString().plus("°")
        binding.weatherDescriptionTextView.text = forecast.weather.description
    }

    private fun setDetailsItemValues(forecast: Forecast) {
        binding.sunriseTimeDetailsItem.forecastDetailsValue.text =
            DateUtil.getTimeText(forecast.sunrise_ts)
        binding.sunsetTimeDetailsItem.forecastDetailsValue.text =
            DateUtil.getTimeText(forecast.sunset_ts)
        val windSpeedUnit = if (SharedPrefs.isCelsius) " m/s" else " mph"
        binding.windSpeedDetailsItem.forecastDetailsValue.text =
            forecast.wind_spd.toString().plus(windSpeedUnit)
        binding.windDirectionDetailsItem.forecastDetailsValue.text =
            forecast.wind_cdir_full.capitalize(Locale.ROOT)
        binding.humidityDetailsItem.forecastDetailsValue.text = forecast.rh.toString().plus("%")
        binding.precipitationProbabilityDetailsItem.forecastDetailsValue.text =
            forecast.pop.toString().plus("%")
        val precipUnit = if (SharedPrefs.isCelsius) " mm" else " in"
        binding.liquidPrecipitationDetailsItem.forecastDetailsValue.text =
            forecast.precip.toString().plus(precipUnit)
        binding.pressureDetailsItem.forecastDetailsValue.text =
            forecast.pres.toString().plus(" mbar")
        val visLength = if (SharedPrefs.isCelsius) " km" else " mi"
        binding.visibilityDetailsItem.forecastDetailsValue.text =
            forecast.vis.toString().plus(visLength)
        binding.maxUvIndexDetailsItem.forecastDetailsValue.text = forecast.uv.toString()
    }

    private fun setDetailsItemLabels() {
        binding.sunriseTimeDetailsItem.forecastDetailsLabel.text = getString(R.string.sunrise_label)
        binding.sunsetTimeDetailsItem.forecastDetailsLabel.text = getString(R.string.sunset_label)
        binding.windSpeedDetailsItem.forecastDetailsLabel.text =
            getString(R.string.wind_speed_label)
        binding.windDirectionDetailsItem.forecastDetailsLabel.text =
            getString(R.string.wind_direction_label)
        binding.humidityDetailsItem.forecastDetailsLabel.text = getString(R.string.humidity_label)
        binding.precipitationProbabilityDetailsItem.forecastDetailsLabel.text =
            getString(R.string.precipitation_probability_label)
        binding.liquidPrecipitationDetailsItem.forecastDetailsLabel.text =
            getString(R.string.accumulated_precipitation_label)
        binding.pressureDetailsItem.forecastDetailsLabel.text =
            getString(R.string.average_pressure_label)
        binding.visibilityDetailsItem.forecastDetailsLabel.text =
            getString(R.string.visibility_label)
        binding.maxUvIndexDetailsItem.forecastDetailsLabel.text =
            getString(R.string.max_uv_index_label)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val direction =
                    ForecastDetailsFragmentDirections.actionForecastDetailsFragmentToSettingsFragment()
                findNavController().navigate(direction)
            }
            R.id.share -> {
                // TODO: Share the forecast details as a text
            }
        }
        return super.onOptionsItemSelected(item)
    }
}