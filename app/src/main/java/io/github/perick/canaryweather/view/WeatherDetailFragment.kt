package io.github.perick.canaryweather.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.perick.canaryweather.R
import io.github.perick.canaryweather.viewmodel.WeatherDetailViewModel

class WeatherDetailFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherDetailFragment()
    }

    private lateinit var viewModel: WeatherDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}