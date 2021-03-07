package com.crnkic.weatherapp.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.util.Prefs
import kotlinx.android.synthetic.main.layout_settings_days.*
import kotlinx.android.synthetic.main.layout_settings_item.view.*
import kotlinx.android.synthetic.main.layout_settings_notification.*
import kotlinx.android.synthetic.main.layout_settings_unit.*

class SettingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getSpinner()

    }

    private fun getSpinner() {
        days_settings_spinner.onItemSelectedListener = this
        context?.let { context ->
            ArrayAdapter.createFromResource(
                    context,
                    R.array.days_array,
                    android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                days_settings_spinner.adapter = adapter
            }
        }

        activity?.let { activity ->
            Prefs.loadDaysSettingsValue(activity).let {
                if (it == 7) {
                    days_settings_spinner.setSelection(0)
                } else {
                    days_settings_spinner.setSelection(1)
                }
            }
        }
    }

    private fun initViews() {
        setSettingsTitles()
        setSettingsSubtitles()
        setClickListenesrs()
    }

    //unit changer
    private fun setClickListenesrs() {
        activity?.let { mActivity ->
            unit_settings_item.setOnClickListener {
                val isCelsius = Prefs.retrieveIsCelsiusSetting(mActivity)
                Prefs.setIsCelsiusSettings(mActivity, !isCelsius)
                setUnitSubtitle(!isCelsius)
            }
        }
    }

    //ALL SUBTITLES
    private fun setSettingsSubtitles() {
        //unit subtitle
        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            setUnitSubtitle(isCelsius)
        }

        //days subtitle
        activity?.let {
            val day = Prefs.loadDaysSettingsValue(it)
            setDaySubtitle(day)
        }

        //TODO: Get and set, also for notification and Days settings

    }

    //DAYS
    private fun setDaySubtitle(day: Int) {
        if (day == 7) {
            days_settings_item.settings_value.text = resources.getStringArray(R.array.days_array)[0]
        } else {
            days_settings_item.settings_value.text = resources.getStringArray(R.array.days_array)[1]
        }

    }

    //UNIT
    private fun setUnitSubtitle(isCelsius: Boolean) {
        unit_settings_item.settings_value.text = if (isCelsius) {
            getString(R.string.celsius_subtitle)
        } else {
            getString(R.string.fahrenheit_subtitle)
        }
    }

    //ALL TITLES
    private fun setSettingsTitles() {
        notification_settings_item.settings_name.text = getString(R.string.weather_notification_setting_title)
        unit_settings_item.settings_name.text = getString(R.string.unit_setting_title)
        days_settings_item.settings_name.text = getString(R.string.days_setting_title)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        activity?.let {
            val value = resources.getStringArray(R.array.days_array)[position]
            Prefs.setDaysSettings(it, value.toInt())
            setDaySubtitle(value.toInt())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}