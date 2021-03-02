package com.samil.app.theweather.screen.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samil.app.theweather.R
import com.samil.app.theweather.utils.Prefs
import kotlinx.android.synthetic.main.layout_settings_days.*
import kotlinx.android.synthetic.main.layout_settings_item.view.*
import kotlinx.android.synthetic.main.layout_settings_notification.*
import kotlinx.android.synthetic.main.layout_settings_unit.*

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
        setSettingsTitles()
        setSettingsSubTitles()
        setClickListeners()
    }

    private fun setClickListeners() {
        activity?.let {mActivity ->
            unit_settings_item.setOnClickListener {
                val isCelsius = Prefs.retrieveIsCelsiusSetting(mActivity)
                Prefs.setIsCelsiusSetting(mActivity, !isCelsius)
                setUnitSubtitle(!isCelsius)
            }
        }
    }

    private fun setSettingsTitles(){
        notification_settings_item.settings_name.text = getString(R.string.weather_notification_setting_title)
        unit_settings_item.settings_name.text = getString(R.string.unit_setting_title)
        days_settings_item.settings_name.text = getString(R.string.days_setting_title)
    }

    private fun setSettingsSubTitles() {
        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            setUnitSubtitle(isCelsius)
        }
    }

    private fun setUnitSubtitle(isCelsius: Boolean) {
        unit_settings_item.settings_value.text = if (isCelsius){
            getString(R.string.celsius_subtitle)
        }else {
            getString(R.string.fahrenheit_subtitle)
        }
    }


}