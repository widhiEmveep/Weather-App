package com.example.weatherapp.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.utils.navController
import com.example.weatherapp.utils.navigateOrNull

class SplashScreenFragment: Fragment(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apply {
            activity?.window?.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                statusBarColor = context.resources.getColor(R.color.white)
            }
        }

        Handler().postDelayed({
            navController.navigateOrNull(
                SplashScreenFragmentDirections.actionToHome()
            )
        },4000)
    }
}