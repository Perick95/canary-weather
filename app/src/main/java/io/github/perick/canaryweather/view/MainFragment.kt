package io.github.perick.canaryweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.databinding.MainFragmentBinding
import io.github.perick.canaryweather.viewmodel.MainViewModel
import io.github.perick.canaryweather.view.adapter.ViewPagerAdapter
import io.github.perick.canaryweather.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (requireActivity().application as CanaryApplication).weatherRepository, (requireActivity().application as
                    CanaryApplication).weatherDetailRepository
        )
    }

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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


    }

    private fun observeViewModel() {
        viewModel.forecastWeather.observe(viewLifecycleOwner, {

        })

        viewModel.allWeathers.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.viewpager.adapter = ViewPagerAdapter(this, it.map { dayWeather -> dayWeather.idWeatherDetail })
            }

        })

        viewModel.apiError.observe(viewLifecycleOwner, {
//            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}