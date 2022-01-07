package io.github.perick.canaryweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.perick.canaryweather.CanaryApplication
import io.github.perick.canaryweather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}