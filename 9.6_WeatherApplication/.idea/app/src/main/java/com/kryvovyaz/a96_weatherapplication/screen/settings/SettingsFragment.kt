package com.kryvovyaz.a96_weatherapplication.screen.settings

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kryvovyaz.a96_weatherapplication.Application
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.*

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSettingsBinding? = null
    private var _bindingDays: LayoutSettingsDaysBinding? = null
    private var _bindingUnits: LayoutSettingsUnitBinding? = null
    private var _bindingNotification: LayoutSettingsNotificationBinding? = null
    private var _bindingLocation: LayoutSettingsLocationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _bindingDays = binding.settingDays
        _bindingUnits = binding.settingsUnits
        _bindingNotification = binding.settingsNotifications
        _bindingLocation = binding.settingsLocation
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadSpinnerSelectedDays()
        setDegreeViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
       // loadSpinnerSelectedDays()
        _bindingDays?.daySettingsSpinner?.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.days_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            _bindingDays?.daySettingsSpinner?.adapter = adapter
        }
    }

    private fun initViews() {
        setSettingsTitles()
        setSettingsSubtitles()
        setClickListeners()
    }

    private fun setClickListeners() {
        _bindingUnits?.celsiusDegreeTextView?.setOnClickListener {
            Application.prefs?.icCelsius = true
            setDegreeViews()
            setUnitSubtitle()
        }
        _bindingUnits?.fahrenheitDegreeTextView?.setOnClickListener {
            Application.prefs?.icCelsius = false
            setDegreeViews()
            setUnitSubtitle()
        }
    }


    private fun setSettingsTitles() {
        _bindingNotification?.notificationSettingsItem?.settingsName?.text =
            getString(R.string.weather_notification_setting_title)
        _bindingUnits?.unitsSettingsItem?.settingsName?.text =
            getString(R.string.units_settings_label)
        _bindingDays?.daysSettingsItem?.settingsName?.text = getString(R.string.days_setting_title)
        _bindingLocation?.locationSettingsItem?.settingsName?.text =
            getString(R.string.settings_location_title)
    }

    private fun setDegreeViews() {

        if (Application.prefs!!.icCelsius) {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.celsiusDegreeTextView?.typeface = Typeface.DEFAULT_BOLD
            _bindingUnits?.fahrenheitDegreeTextView?.typeface=Typeface.DEFAULT
        } else {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.celsiusDegreeTextView?.typeface = Typeface.DEFAULT
            _bindingUnits?.fahrenheitDegreeTextView?.typeface=Typeface.DEFAULT_BOLD
        }
    }

    private fun setSettingsSubtitles() {
        setUnitSubtitle()
        setDaysSubtitle()
        setLocationSubtitle()
        //TODO: Get and set, also for notification settings
    }
private fun showmap(){
    if(!_bindingLocation?.locationSettingsSwitch?.isChecked!!){

    }

}
    private fun setLocationSubtitle() {
        _bindingLocation?.locationSettingsItem?.settingsName?.text =
            getString(R.string.current_location)
    }

    private fun setDaysSubtitle() {
        _bindingDays?.daysSettingsItem?.settingsValue?.text = getString(R.string.days_selected).plus(
            resources.getString(
                R.string.space
            )
        ).plus(if (Application.prefs!!.days == 14) 14 else 7)

    }

    private fun setUnitSubtitle() {

        _bindingUnits?.unitsSettingsItem?.settingsValue?.text = if (Application.prefs!!.icCelsius) {
            getString(R.string.degree_celsius)
        } else {
            getString(R.string.degree_fahrenheit)
        }
    }

    private fun loadSpinnerSelectedDays() {
        _bindingDays?.daySettingsSpinner?.setSelection(if (Application.prefs!!.days == 14) 0 else 1)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        let {
            (parent?.getChildAt(0) as TextView)
                .setTextColor(resources.getColor(R.color.bar_header))
            Application.prefs?.days = if (position == 0) 14 else 7
            setDaysSubtitle()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _bindingLocation = null
        _bindingNotification = null
        _bindingDays = null
        _bindingUnits = null
    }
//   override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//       _bindingDays?.daySettingsSpinner?.let { outState.putInt(
//           "SpinnerPosition",
//           it.selectedItemPosition
//       ) }
//
//    }
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        when (newConfig.orientation) {
//            Configuration.ORIENTATION_LANDSCAPE -> {loadSpinnerSelectedDays()
//            }
//            Configuration.ORIENTATION_PORTRAIT -> {loadSpinnerSelectedDays()
//            }
//        }
//    }
}