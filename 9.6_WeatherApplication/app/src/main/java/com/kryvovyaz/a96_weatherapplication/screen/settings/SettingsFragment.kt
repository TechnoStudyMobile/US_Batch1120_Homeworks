package com.kryvovyaz.a96_weatherapplication.screen.settings

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
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.*
import com.kryvovyaz.a96_weatherapplication.util.App


class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSettingsBinding? = null
//    private var _bindingsettings: FragmentSettingsBinding? = null

    private var _bindingDays: LayoutSettingsDaysBinding? = null
    private var _bindingUnits: LayoutSettingsUnitBinding? = null
    private var _bindingNotification: LayoutSettingsNotificationBinding? = null
    private var _bindingLocation: LayoutSettingsLocationBinding? = null
    private val binding get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _bindingDays =binding.settingDays
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
        loadSpinnerSelectedDays()
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

        _bindingUnits?.celsiusDegreeTextView?.setOnClickListener { celsius_degree ->
            App.prefs?.icCelsius = true
            setDegreeViews()
            setUnitSubtitle()
        }
        _bindingUnits?.fahrenheitDegreeTextView?.setOnClickListener {
            App.prefs?.icCelsius = false
            setDegreeViews()
            setUnitSubtitle()
        }
    }


    private fun setSettingsTitles() {
       _bindingNotification?.notificationSettingsItem?.settingsName?.text =
            getString(R.string.weather_notification_setting_title)
       _bindingUnits?.unitsSettingsItem?.settingsName?.text = getString(R.string.units_settings_label)
        _bindingDays?.daysSettingsItem?.settingsName?.text = getString(R.string.days_setting_title)
       _bindingLocation?.locationSettingsItem?.settingsName?.text = getString(R.string.settings_location_title)
    }

    private fun setDegreeViews() {

        if (App.prefs!!.icCelsius) {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.celsiusDegreeTextView?.setTypeface(Typeface.DEFAULT_BOLD)
            _bindingUnits?.fahrenheitDegreeTextView?.setTypeface(Typeface.DEFAULT)
        } else {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.celsiusDegreeTextView?.setTypeface(Typeface.DEFAULT)
            _bindingUnits?.fahrenheitDegreeTextView?.setTypeface(Typeface.DEFAULT_BOLD)
        }
    }

    private fun setSettingsSubtitles() {
        setUnitSubtitle()
        setDaysSubtitle()
        setLocationSubtitle()
        //TODO: Get and set, also for notification settings
    }

    private fun setLocationSubtitle() {
        _bindingLocation?.locationSettingsItem?.settingsName?.text = getString(R.string.current_location)
    }

    private fun setDaysSubtitle() {
        _bindingDays?.daysSettingsItem?.settingsName?.text = getString(R.string.days_selected).plus(
            resources.getString(
                R.string.space
            )
        ).plus(if (App.prefs!!.days == 14) 14 else 7)

    }

    private fun setUnitSubtitle() {

        _bindingUnits?.unitsSettingsItem?.settingsValue?.text = if (App.prefs!!.icCelsius) {
            getString(R.string.degree_celsius)
        } else {
            getString(R.string.degree_fahrenheit)
        }
    }

    private fun loadSpinnerSelectedDays() {
        _bindingDays?.daySettingsSpinner?.setSelection(if (App.prefs!!.days == 14) 0 else 1)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (parent?.getChildAt(0) as TextView)
            .setTextColor(resources.getColor(R.color.bar_header))
        App.prefs?.days = if (position == 0) 14 else 7
        setDaysSubtitle()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _bindingLocation=null
        _bindingNotification=null
        _bindingDays=null
        _bindingUnits=null
    }
}