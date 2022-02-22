package com.example.weatherapp.presentation.fragment

import android.annotation.SuppressLint
import android.app.Service
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Looper
import android.view.ContextThemeWrapper
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.viewBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<WeatherViewModel>()

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onFetchData()
        initFetchData()
    }

    private fun initFetchData() {
        viewModel.getCurrentWeather(0.0, 0.0)
    }

    private fun onFetchData() {
        viewModel.currentWeather.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    //binding.tvTemperature.text = resources.getString(R.string.temp_format).format(result.data?.main?.temp)
                    binding.tvTemperature.text = "${result.data?.main?.temp}"
                    binding.tvWeather.text = result.data?.weather?.get(0)?.main
                    binding.tvWindSpeed.text = result.data?.wind?.speed.toString()
                    binding.tvHumidity.text = result.data?.main?.humidity.toString()
                    binding.tvCloudiness.text = result.data?.clouds?.all.toString()
                    binding.toolbar.setTitle(result.data?.name.orEmpty())

                    binding.toolbar.searchEditText.setOnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            performSearch(v.text.toString())
                            true
                        }
                        false
                    }
                }
                is Result.Loading -> {
                }
                is Result.Error -> {
                }
            }
        })
    }

    private fun performSearch(cityName: String) {

    }
}