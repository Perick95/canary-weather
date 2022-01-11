package io.github.perick.canaryweather.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.R
import io.github.perick.canaryweather.repository.remote.model.ForecastWeatherRequest
import io.github.perick.canaryweather.viewmodel.MainViewModel
import io.github.perick.canaryweather.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    //TODO move api key await from here in a production environment
    val API_KEY = "5f7ea4896cc3a871301c0c09d04a04b2"
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as CanaryApplication).weatherRepository, (application as
                    CanaryApplication).weatherDetailRepository
        )
    }

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
                Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        if (allPermissionsGranted()) {
            retrieveLocation()
        } else {
            requestMultiplePermissionsLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun retrieveLocation() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
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
            applicationContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}