package com.mitocode.marketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mitocode.marketapp.databinding.ActivityMenuMainBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MenuMainHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.menu_nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}