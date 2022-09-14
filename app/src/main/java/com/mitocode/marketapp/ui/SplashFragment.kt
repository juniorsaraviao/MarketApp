package com.mitocode.marketapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mitocode.marketapp.R

class SplashFragment : Fragment() {

    private val SPLASH_TIME_OUT: Long = 4000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToScreenLogin(view)
    }

    private fun goToScreenLogin(view: View) {
        Handler(Looper.getMainLooper()).postDelayed({
            // Go to Login action_splash
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
        }, SPLASH_TIME_OUT)
    }
}