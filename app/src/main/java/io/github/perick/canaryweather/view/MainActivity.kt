package io.github.perick.canaryweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.perick.canaryweather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}