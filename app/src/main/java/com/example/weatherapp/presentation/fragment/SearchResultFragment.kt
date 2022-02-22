package com.example.weatherapp.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.local.entity.FavoriteCity
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.viewBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_home) {

    private val args: SearchResultFragmentArgs by navArgs()
    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<WeatherViewModel>()

    private val weatherListAdapter = WeatherListAdapter()
    private var cityName = emptyList<String>()

    var status = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFetchData(args.cityName)
        initUI()
        setAdapter()
        initWeatherList()
        initSwipeRefresh()
    }

    private fun setAdapter() {
        binding.rvWeatherList.adapter = weatherListAdapter
    }

    private fun initFetchData(cityName: String) {
        viewModel.getWeatherByCity(cityName)
        viewModel.getFavCityResult()
    }

    private fun initSwipeRefresh() {
        binding.swipeLayout.setOnRefreshListener {
            initFetchData(args.cityName)
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
        viewModel.favCity.observe(viewLifecycleOwner, { result ->
            Timber.e(result.toString())
            cityName = result.map { it.cityName }
        })

        viewModel.weatherByCityName.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    status = cityName.contains(result.data?.name) != true
                    if (status) binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                    else binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                    binding.tvTemperature.text = "${result.data?.main?.temp} \u2103"
                    binding.tvWeather.text = result.data?.weather?.get(0)?.main
                    binding.tvWindSpeed.text = result.data?.wind?.speed.toString() + " m/s"
                    binding.tvHumidity.text = result.data?.main?.humidity.toString() + "%"
                    binding.tvCloudiness.text = result.data?.clouds?.all.toString() + "%"
                    binding.toolbar.setTitle(result.data?.name.orEmpty())

                    binding.toolbar.setLeftIconImage(1)
                    binding.toolbar.setSearchIcon(false)

                    binding.toolbar.leftIcon.setOnClickListener {
                        navController.navigateUp()
                    }

                    val lat = result.data?.coord?.lat!!
                    val lon = result.data.coord?.lon!!
                    viewModel.getForecastWeather(lat, lon)

                    addToFavorite(result.data)
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

    private fun addToFavorite(result: CurrentWeatherModel) {
        val city = FavoriteCity(
            id = 1,
            lat = result.coord?.lat!!,
            lon = result.coord?.lon!!,
            cityName = result.name!!
        )
        binding.ivFavorite.setOnClickListener {
            if (status) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                status = false

                viewModel.insertFavCityResult(city)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_add_fav_city),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                status = true

                viewModel.deleteFavCityResult(result.name!!)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_delete_fav_city),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}