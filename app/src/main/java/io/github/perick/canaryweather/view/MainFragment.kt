package io.github.perick.canaryweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.databinding.MainFragmentBinding
import io.github.perick.canaryweather.viewmodel.MainViewModel
import io.github.perick.canaryweather.repository.ForecastWeatherRequest
import io.github.perick.canaryweather.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    val API_KEY = "5f7ea4896cc3a871301c0c09d04a04b2"

    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((requireActivity().application as CanaryApplication).weatherRepository)
    }

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.btForecast.setOnClickListener {
            viewModel.getForecastWeather(ForecastWeatherRequest("35", "139", "7", API_KEY))
        }

    }

    private fun observeViewModel() {
        viewModel.forecastWeather.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "YEEE", Toast.LENGTH_SHORT).show()
        })

        viewModel.allWeathers.observe(viewLifecycleOwner, {
            if(it.size > 0) {
                val weather = it[0]
                binding.tvWeather.text = weather.main

                binding.viewpager.adapter = ViewPagerAdapter(this, it.map { dayWeather -> dayWeather.idWeather })
            }

        })

        viewModel.apiError.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}