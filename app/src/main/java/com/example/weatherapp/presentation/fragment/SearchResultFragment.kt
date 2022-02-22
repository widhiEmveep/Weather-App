package com.example.weatherapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.viewBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_home) {

    private val args : SearchResultFragmentArgs by navArgs()
    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<WeatherViewModel>()

    private val weatherListAdapter = WeatherListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFetchData(args.cityName)
        initUI()
        setAdapter()
        initWeatherList()
    }

    private fun setAdapter() {
        binding.rvWeatherList.adapter = weatherListAdapter
    }

    private fun initFetchData(cityName: String) {
        viewModel.getWeatherByCity(cityName)
    }

    private fun initUI() {
        viewModel.weatherByCityName.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    //binding.tvTemperature.text = resources.getString(R.string.temp_format).format(result.data?.main?.temp)
                    binding.tvTemperature.text = "${result.data?.main?.temp}"
                    binding.tvWeather.text = result.data?.weather?.get(0)?.main
                    binding.tvWindSpeed.text = result.data?.wind?.speed.toString()
                    binding.tvHumidity.text = result.data?.main?.humidity.toString()
                    binding.tvCloudiness.text = result.data?.clouds?.all.toString()
                    binding.toolbar.setTitle(result.data?.name.orEmpty())

                    binding.toolbar.setLeftIconImage(1)
                    binding.toolbar.setSearchIcon(false)

                    binding.toolbar.leftIcon.setOnClickListener {
                        navController.navigateUp()
                    }

                    val lat = result.data?.coord?.lat!!
                    val lon = result.data.coord?.lon!!
                    viewModel.getForecastWeather(lat, lon)
                }
                is Result.Loading -> {
                }
                is Result.Error -> {
                }
            }
        })
    }

    private fun initWeatherList() {
        viewModel.forecastWeather.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    weatherListAdapter.items = result.data?.list.orEmpty()
                }
            }
        })
    }
}