package com.mitocode.marketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mitocode.marketapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_host)

        Thread.sleep(3000)
        splash.setKeepOnScreenCondition { false }
    }
}