package io.github.perick.canaryweather.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.R
import io.github.perick.canaryweather.databinding.MainFragmentBinding
import io.github.perick.canaryweather.databinding.WeatherFragmentBinding
import io.github.perick.canaryweather.viewmodel.*

class WeatherFragment(val dayTimestamp: Long) : Fragment() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(dayTimestamp, (requireActivity().application as CanaryApplication).weatherRepository)
    }

    private lateinit var viewModelStore: ViewModelStore

    private var _binding: WeatherFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tectview.text = dayTimestamp.toString()
        viewModelStore = ViewModelProvider(requireActivity())[ViewModelStore::class.java]

        observeViewModel()
    }



    private fun observeViewModel() {
        viewModel.weather.observe(viewLifecycleOwner, { dayWeather ->
            dayWeather?.let {
                binding.tectview.text = it.description

                binding.btDetail.setOnClickListener { view ->
                    //FIXME replace with navigation component
                    viewModelStore.weatherIdSelected.postValue(it.idWeatherDetail)
                    (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, WeatherDetailFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}