package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.screen.view.WeatherAdapter
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastListFragment : Fragment() {
    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)
        viewModel.fetchForecastInfo()
        viewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            createRecyclerList(it)
        })
    }

    private fun createRecyclerList(forecast: Forecast) {
        val adapter = WeatherAdapter(forecast) { position ->
            val direction =
                ForecastListFragmentDirections.actionForecastListFragmentToForecastDetailsFragment(
                    position
                )
            findNavController().navigate(direction)
        }
        weather_recycler_view.layoutManager = LinearLayoutManager(context)
        weather_recycler_view.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map_location-> Toast.makeText(
                context,
                "Search location option to be implemented",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> {
                val directions =
                    ForecastListFragmentDirections.actionForecastListFragmentToSettingsFragment()
                findNavController()
                    .navigate(directions)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}