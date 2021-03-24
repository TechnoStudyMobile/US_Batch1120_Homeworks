package ustun.muharrem.weatherforecast.screens.settings

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.layout_settings_days.*
import kotlinx.android.synthetic.main.layout_settings_item.view.*
import kotlinx.android.synthetic.main.layout_settings_notification.*
import kotlinx.android.synthetic.main.layout_settings_units.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setClickListeners()
        setSettingsTitles()
        setSettingsSubtitles()
        setDegreeViews()
        setNotificationsCheckbox()
        setNumberOfDaysSpinner()
    }

    private fun setClickListeners() {

        celsius_degree_text_view.setOnClickListener {
            SharedPrefs.isCelsius = true
            setDegreeViews()
            setUnitSubtitle()
        }
        fahrenheit_degree_text_view.setOnClickListener {
            SharedPrefs.isCelsius = false
            setDegreeViews()
            setUnitSubtitle()
        }

        days_settings_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val numberOfDays: String = parent?.getItemAtPosition(position).toString()
                SharedPrefs.numberOfDays = numberOfDays
                setNumberOfDaysSubtitle()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        notification_settings_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            SharedPrefs.notificationEnabled = isChecked
            setNotificationsSubtitle()
            val toastMessage =
                if (isChecked) getString(R.string.weather_notifications_enabled) else getString(
                    R.string.weather_notifications_disabled
                )
            Toast.makeText(requireActivity(), toastMessage, Toast.LENGTH_SHORT).show()
            //  TODO other checkbox listeners actions to send notifications!!!
        }
    }

    private fun setNotificationsCheckbox() {
        notification_settings_checkbox.isChecked = SharedPrefs.notificationEnabled
    }

    private fun setDegreeViews() {
        if (SharedPrefs.isCelsius) {
            celsius_degree_text_view.setTextColor(Color.BLACK)
            fahrenheit_degree_text_view.setTextColor(Color.GRAY)
            celsius_degree_text_view.setTypeface(Typeface.DEFAULT_BOLD)
            fahrenheit_degree_text_view.setTypeface(Typeface.DEFAULT)
        } else {
            celsius_degree_text_view.setTextColor(Color.GRAY)
            fahrenheit_degree_text_view.setTextColor(Color.BLACK)
            celsius_degree_text_view.setTypeface(Typeface.DEFAULT)
            fahrenheit_degree_text_view.setTypeface(Typeface.DEFAULT_BOLD)
        }
    }

    private fun setSettingsSubtitles() {
        setUnitSubtitle()
        setNumberOfDaysSubtitle()
        setNotificationsSubtitle()
    }

    private fun setUnitSubtitle() {
        units_settings_item.settings_value.text = if (SharedPrefs.isCelsius) {
            getString(R.string.celsius)
        } else {
            getString(R.string.fahrenheit)
        }
    }

    private fun setNotificationsSubtitle() {
        notification_settings_item.settings_value.text = if (SharedPrefs.notificationEnabled) {
            getString(R.string.enabled)
        } else {
            getString(R.string.disabled)
        }
    }

    private fun setNumberOfDaysSubtitle() {
        days_settings_item.settings_value.text =
            SharedPrefs.numberOfDays.plus(getString(R.string.day_forecast))
    }

    private fun setSettingsTitles() {
        notification_settings_item.settings_name.text =
            getString(R.string.weather_notification_settings_label)
        units_settings_item.settings_name.text = getString(R.string.units_settings_label)
        days_settings_item.settings_name.text = getString(R.string.number_days_settings_label)
    }

    private fun setNumberOfDaysSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(), R.array.number_of_days, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        days_settings_spinner.adapter = adapter
        // Selects the default number of days value for the first time
        val position: Int = adapter.getPosition(SharedPrefs.numberOfDays)
        days_settings_spinner.setSelection(position)
    }
}