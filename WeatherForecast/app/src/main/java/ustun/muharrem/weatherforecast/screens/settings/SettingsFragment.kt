package ustun.muharrem.weatherforecast.screens.settings

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.databinding.FragmentSettingsBinding
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
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

        binding.layoutSettingsUnits.celsiusDegreeTextView.setOnClickListener {
            SharedPrefs.isCelsius = true
            setDegreeViews()
            setUnitSubtitle()
        }
        binding.layoutSettingsUnits.fahrenheitDegreeTextView.setOnClickListener {
            SharedPrefs.isCelsius = false
            setDegreeViews()
            setUnitSubtitle()
        }

        binding.layoutSettingsDays.daysSettingsSpinner.onItemSelectedListener = object :
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

        binding.layoutSettingsNotification.notificationSettingsCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
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
        binding.layoutSettingsNotification.notificationSettingsCheckbox.isChecked =
            SharedPrefs.notificationEnabled
    }

    private fun setDegreeViews() {
        if (SharedPrefs.isCelsius) {
            binding.layoutSettingsUnits.celsiusDegreeTextView.setTextColor(Color.BLACK)
            binding.layoutSettingsUnits.fahrenheitDegreeTextView.setTextColor(Color.GRAY)
            binding.layoutSettingsUnits.celsiusDegreeTextView.setTypeface(Typeface.DEFAULT_BOLD)
            binding.layoutSettingsUnits.fahrenheitDegreeTextView.setTypeface(Typeface.DEFAULT)
        } else {
            binding.layoutSettingsUnits.celsiusDegreeTextView.setTextColor(Color.GRAY)
            binding.layoutSettingsUnits.fahrenheitDegreeTextView.setTextColor(Color.BLACK)
            binding.layoutSettingsUnits.celsiusDegreeTextView.setTypeface(Typeface.DEFAULT)
            binding.layoutSettingsUnits.fahrenheitDegreeTextView.setTypeface(Typeface.DEFAULT_BOLD)
        }
    }

    private fun setSettingsSubtitles() {
        setUnitSubtitle()
        setNumberOfDaysSubtitle()
        setNotificationsSubtitle()
    }

    private fun setUnitSubtitle() {
        binding.layoutSettingsUnits.unitsSettingsItem.settingsValue.text =
            if (SharedPrefs.isCelsius) {
                getString(R.string.celsius)
            } else {
                getString(R.string.fahrenheit)
            }
    }

    private fun setNotificationsSubtitle() {
        binding.layoutSettingsNotification.notificationSettingsItem.settingsValue.text =
            if (SharedPrefs.notificationEnabled) {
                getString(R.string.enabled)
            } else {
                getString(R.string.disabled)
            }
    }

    private fun setNumberOfDaysSubtitle() {
        binding.layoutSettingsDays.daysSettingsItem.settingsValue.text =
            SharedPrefs.numberOfDays.plus(getString(R.string.day_forecast))
    }

    private fun setSettingsTitles() {
        binding.layoutSettingsNotification.notificationSettingsItem.settingsName.text =
            getString(R.string.weather_notification_settings_label)
        binding.layoutSettingsUnits.unitsSettingsItem.settingsName.text = getString(R.string.units_settings_label)
        binding.layoutSettingsDays.daysSettingsItem.settingsName.text = getString(R.string.number_days_settings_label)
    }

    private fun setNumberOfDaysSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(), R.array.number_of_days, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.layoutSettingsDays.daysSettingsSpinner.adapter = adapter
        // Selects the default number of days value for the first time
        val position: Int = adapter.getPosition(SharedPrefs.numberOfDays)
        binding.layoutSettingsDays.daysSettingsSpinner.setSelection(position)
    }
}