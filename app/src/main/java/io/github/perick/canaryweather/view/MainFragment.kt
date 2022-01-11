package io.github.perick.canaryweather.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.databinding.MainFragmentBinding
import io.github.perick.canaryweather.viewmodel.MainViewModel
import io.github.perick.canaryweather.repository.remote.model.ForecastWeatherRequest
import io.github.perick.canaryweather.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    //TODO move api key await from here in a production environment
    val API_KEY = "5f7ea4896cc3a871301c0c09d04a04b2"
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var isPermissionGranted = true
            permissions.entries.forEach {
                Log.e("DEBUG", "${it.key} = ${it.value}")
                isPermissionGranted = isPermissionGranted && it.value
            }

            if (isPermissionGranted) {
                retrieveLocation()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }


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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (allPermissionsGranted()) {
            retrieveLocation()
        } else {
            requestMultiplePermissionsLauncher.launch(REQUIRED_PERMISSIONS)
        }

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

    //    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    private fun retrieveLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissionsLauncher.launch(REQUIRED_PERMISSIONS)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                viewModel.getForecastWeather(
                    ForecastWeatherRequest(
                        location?.latitude.toString(), location?.longitude.toString(), "7",
                        API_KEY
                    )
                )
            }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}