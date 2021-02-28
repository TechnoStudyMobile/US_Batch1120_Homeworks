package com.kryvovyaz.a96_weatherapplication.screen.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getInt
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.util.IS_CELSIUS_SETTING_PREF_KEY
import com.kryvovyaz.a96_weatherapplication.util.IS_DAYS_SELECTED_KEY
import com.kryvovyaz.a96_weatherapplication.util.Prefs
import kotlinx.android.synthetic.main.layout_settings_days.*
import kotlinx.android.synthetic.main.layout_settings_item.view.*
import kotlinx.android.synthetic.main.layout_settings_notification.*
import kotlinx.android.synthetic.main.layout_settings_unit.*

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onResume() {
        super.onResume()
        loadSpinnerSelectedDays()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadSpinnerSelectedDays()
        activity?.getPreferences(Context.MODE_PRIVATE)?.let {
            day_settings_spinner.setSelection(
                it.getInt(
                    IS_DAYS_SELECTED_KEY, 0
                )
            )
        }

        day_settings_spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.days_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            day_settings_spinner.adapter = adapter
        }
    }

    private fun initViews() {
        setSettingsTitles()
        setSettingsSubtitles()
        setClickListeners()
    }

    private fun setClickListeners() {
        activity?.let { mActivity ->
            unit_settings_item.setOnClickListener {
                val isCelsius = Prefs.retrieveIsCelsiusSetting(mActivity)
                Prefs.setIsCelsiusSetting(mActivity, !isCelsius)
                setUnitSubtitle(!isCelsius)
            }
        }
    }

    private fun setSettingsTitles() {
        notification_settings_item.settings_name.text =
            getString(R.string.weather_notification_setting_title)
        unit_settings_item.settings_name.text = getString(R.string.unit_setting_title)
        days_settings_item.settings_name.text = getString(R.string.days_setting_title)
    }

    private fun setSettingsSubtitles() {
        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            setUnitSubtitle(isCelsius)
            setDaysSubtitle()
            //TODO: Get and set, also for notification and Days settings
        }
    }

    private fun setDaysSubtitle() {
        activity?.let {
            days_settings_item.settings_value.text = getString(R.string.days_selected)
        }
    }

    private fun setUnitSubtitle(isCelsius: Boolean) {
        unit_settings_item.settings_value.text = if (isCelsius) {
            getString(R.string.celsius_subtitle)
        } else {
            getString(R.string.fahrenheit_subtitle)
        }
    }

    private fun loadSpinnerSelectedDays() {
        activity?.let { Prefs.retrieveSpinnerPosition(it) }?.let {
            day_settings_spinner.setSelection(
                it
            )
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        activity?.let {
            Prefs.setDaysPreferenceSelected(it, day_settings_spinner)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}