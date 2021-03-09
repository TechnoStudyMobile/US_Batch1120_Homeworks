package com.kryvovyaz.a96_weatherapplication.screen.settings

import android.content.Context
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
        setDegreeViews()
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
            celsius_degree_text_view.setOnClickListener { celsius_degree ->
                Prefs.setIsCelsiusSetting(mActivity, true)
                setDegreeViews()
                setUnitSubtitle()
            }
            fahrenheit_degree_text_view.setOnClickListener {
                Prefs.setIsCelsiusSetting(mActivity, false)
                setDegreeViews()
                setUnitSubtitle()
            }
        }
    }

    private fun setSettingsTitles() {
        notification_settings_item.settings_name.text =
            getString(R.string.weather_notification_setting_title)
        units_settings_item.settings_name.text = getString(R.string.units_settings_label)
        days_settings_item.settings_name.text = getString(R.string.days_setting_title)
    }

    private fun setDegreeViews() {
        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            if (isCelsius) {
                celsius_degree_text_view.setTextColor(requireActivity().resources.getColor(R.color.bar_header))
                fahrenheit_degree_text_view.setTextColor(Color.GRAY)
                celsius_degree_text_view.setTypeface(Typeface.DEFAULT_BOLD)
                fahrenheit_degree_text_view.setTypeface(Typeface.DEFAULT)
            } else {
                celsius_degree_text_view.setTextColor(Color.GRAY)
                fahrenheit_degree_text_view.setTextColor(requireActivity().resources.getColor(R.color.bar_header))
                celsius_degree_text_view.setTypeface(Typeface.DEFAULT)
                fahrenheit_degree_text_view.setTypeface(Typeface.DEFAULT_BOLD)
            }
        }
    }

    private fun setSettingsSubtitles() {
        activity?.let {
            setUnitSubtitle()
            setDaysSubtitle()
            //TODO: Get and set, also for notification settings
        }
    }

    private fun setDaysSubtitle() {
        activity?.let {
            days_settings_item.settings_value.text = getString(R.string.days_selected).plus(
                it.resources.getString(
                    R.string.space
                )
            ).plus(Prefs.loadDaysSelected(it).toString())
        }
    }

    private fun setUnitSubtitle() {
        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            units_settings_item.settings_value.text = if (isCelsius) {
                getString(R.string.degree_celsius)
            } else {
                getString(R.string.degree_fahrenheit)
            }
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
            (parent?.getChildAt(0) as TextView)
                .setTextColor(it.resources.getColor(R.color.bar_header))
        }
        setDaysSubtitle()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}