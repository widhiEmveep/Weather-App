package com.example.weatherapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.viewBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding<FragmentFavoriteBinding>()
    private val viewModel by viewModels<WeatherViewModel>()

    private val favoriteCityAdapter = FavoriteCityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        initUI()
        setAdapterOnClick()

        binding.toolbar.setTitle("Favorite City")
        binding.toolbar.leftIcon.setOnClickListener {
            navController.navigateUp()
        }
        binding.toolbar.setSearchIcon(false)

        viewModel.getFavCityResult()
    }

    private fun setAdapter() {
        binding.rvFavoriteCity.adapter = favoriteCityAdapter
    }

    private fun initUI() {
        viewModel.favCity.observe(viewLifecycleOwner, { result ->
            favoriteCityAdapter.items = result
            Timber.e(result.toString())
        })
    }

    private fun setAdapterOnClick() {
        favoriteCityAdapter.onItemClickListenerCallback { cityName ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionToSearchResult(cityName)
            )
        }
    }
}