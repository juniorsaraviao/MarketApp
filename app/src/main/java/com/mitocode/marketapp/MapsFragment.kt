package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mitocode.marketapp.databinding.FragmentMapsBinding

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()
    }

    private fun createMarker() {
        val place = LatLng(-12.0897664, -77.0553024)
        val marker = MarkerOptions().position(place).title("Salaverry Square")
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 18f), 1_000, null)
    }
}