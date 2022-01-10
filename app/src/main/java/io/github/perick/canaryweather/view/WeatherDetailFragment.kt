package io.github.perick.canaryweather.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.R
import io.github.perick.canaryweather.databinding.WeatherDetailFragmentBinding
import io.github.perick.canaryweather.databinding.WeatherFragmentBinding
import io.github.perick.canaryweather.viewmodel.ViewModelStore
import io.github.perick.canaryweather.viewmodel.WeatherDetailViewModel
import io.github.perick.canaryweather.viewmodel.WeatherDetailViewModelFactory
import io.github.perick.canaryweather.viewmodel.WeatherViewModelFactory

class WeatherDetailFragment : Fragment() {


    private var _binding: WeatherDetailFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: WeatherDetailViewModel by viewModels{
        WeatherDetailViewModelFactory((requireActivity().application as CanaryApplication).weatherDetailRepository)
    }
    private lateinit var viewModelStore: ViewModelStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelStore = ViewModelProvider(requireActivity())[ViewModelStore::class.java]

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModelStore.weatherIdSelected.observe(viewLifecycleOwner, {
            viewModel.getWeatherDetail(it)
        })

        viewModel.weatherDetail.observe(viewLifecycleOwner, { detail ->
            detail?.let {
                binding.tvTest.text = it.dt.toString()
            }

        })
    }


}