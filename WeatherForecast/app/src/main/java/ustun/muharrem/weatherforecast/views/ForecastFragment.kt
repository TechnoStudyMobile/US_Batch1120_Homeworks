package ustun.muharrem.weatherforecast.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_forecast.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.adapters.ForecastListAdapter

class ForecastFragment : Fragment() {

    lateinit var forecastViewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        forecastViewModel.setForecastListLiveData()

        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            val linearLayoutManager = LinearLayoutManager(context)
            val forecastListAdapter = ForecastListAdapter(it)
            recycler_view_forecast_fragment.layoutManager = linearLayoutManager
            recycler_view_forecast_fragment.adapter = forecastListAdapter
        })
    }
}