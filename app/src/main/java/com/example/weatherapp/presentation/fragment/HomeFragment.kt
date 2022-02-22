package com.example.weatherapp.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.viewBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<WeatherViewModel>()

    private val weatherListAdapter = WeatherListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initFetchData()
        setAdapter()
        initWeatherList()
        initSwipeRefresh()

        binding.toolbar.leftIcon.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionToFavorite())
        }

        binding.ivFavorite.visibility = View.GONE
    }

    private fun setAdapter() {
        binding.rvWeatherList.adapter = weatherListAdapter
    }

    private fun initFetchData() {
        viewModel.getCurrentWeather(0.0, 0.0)
        viewModel.getFavCityResult()
    }

    private fun initSwipeRefresh() {
        binding.swipeLayout.setOnRefreshListener {
            initFetchData()
            initWeatherList()
            initUI()

            val handler = Handler()
            handler.postDelayed(Runnable {
                if (binding.swipeLayout.isRefreshing) {
                    binding.swipeLayout.isRefreshing = false
                }
            }, 2000)
        }
    }

    private fun initUI() {
        viewModel.currentWeather.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    binding.tvTemperature.text = "${result.data?.main?.temp} \u2103"
                    binding.tvWeather.text = result.data?.weather?.get(0)?.main
                    binding.tvWindSpeed.text = result.data?.wind?.speed.toString() + " m/s"
                    binding.tvHumidity.text = result.data?.main?.humidity.toString() + "%"
                    binding.tvCloudiness.text = result.data?.clouds?.all.toString() + "%"
                    binding.toolbar.setTitle(result.data?.name.orEmpty())

                    binding.toolbar.searchEditText.setOnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            performSearch(v.text.toString())
                            true
                        }
                        false
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

    private fun performSearch(cityName: String) {
        navController.navigate(HomeFragmentDirections.actionToSearchResult(cityName))
        binding.toolbar.searchEditText.clearFocus()
        binding.toolbar.searchEditText.setText("")
    }
}