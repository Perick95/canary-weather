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
import io.github.perick.canaryweather.databinding.WeatherFragmentBinding
import io.github.perick.canaryweather.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*

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
        viewModelStore = ViewModelProvider(requireActivity())[ViewModelStore::class.java]

        observeViewModel()
    }



    private fun observeViewModel() {
        viewModel.weather.observe(viewLifecycleOwner, { dayWeather ->
            dayWeather?.let {
                binding.tvDate.text = getDateString(it.idWeatherDetail)
                binding.tvMainWeather.text = it.main
                binding.tvDescription.text = it.description
                //make the temperature conversation from kelvin to degree
                binding.tvDegree.text = (it.degree - 273).toString() + "°"

                binding.btDetail.setOnClickListener { view ->
                    //FIXME replace with navigation component
                    viewModelStore.weatherIdSelected.postValue(it.idWeatherDetail)
                    (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, WeatherDetailFragment())
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.from_left, R.anim.to_right, R.anim.from_right, R.anim.to_left)
                        .commit()
                }
            }

        })
    }

    private fun getDateString(epoch: Long): String {
        val epochMilliseconds = (epoch.toString().plus("000")).toLong()
//        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val sdf = SimpleDateFormat("EEEE dd MMMM ")
        return sdf.format(Date(epochMilliseconds))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}