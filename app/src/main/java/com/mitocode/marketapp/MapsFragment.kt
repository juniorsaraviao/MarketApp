package com.mitocode.marketapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mitocode.marketapp.databinding.FragmentMapsBinding
import com.mitocode.marketapp.ui.common.toast

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    val coarsePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        when{
            isGranted -> map.isMyLocationEnabled = true
            else -> requireContext().toast("Denied")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)

        enableLocation()
    }

    private fun createMarker() {
        val place = LatLng(-12.0897664, -77.0553024)
        val marker = MarkerOptions().position(place).title("Salaverry Square")
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 18f), 1_000, null)
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){

        if(!::map.isInitialized) return

        if(isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
            onMyLocation()
        }else{
            coarsePermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onMyLocationClick(location: Location) {
        requireContext().toast("Location Lat: ${location.latitude} Long: ${location.longitude}")
    }

    override fun onMyLocationButtonClick(): Boolean {

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            showSettingAlert().show()
            // nothing to execute
            return true
        }
        // if there's all the permission enabled
        return false
    }

    private fun showSettingAlert(): AlertDialog {

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Configuración de GPS")
        alertDialog.setMessage("GPS deshabilitado. Desea habilitar esta configuración?")
        alertDialog.setPositiveButton("Configuración",
            DialogInterface.OnClickListener{ dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
        })

        alertDialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })

        return alertDialog.create()

    }

    @SuppressLint("MissingPermission")
    private fun onMyLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if(it != null){
                    printLocation(it)
                }else{
                    requireContext().toast("No se pudo obtener la ubicación")
                }
            }

            val locationRequest = LocationRequest.create().apply{
                interval = 1_000
                fastestInterval = 5_000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            locationCallback = object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult ?: return
                    println("Se recibió una actualización")
                    for (location in locationResult.locations){
                        printLocation(location)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        }catch (e: Exception){

        }

    }

    private fun printLocation(location: Location?) {
        println("Latitud ${location?.latitude} - Longitud ${location?.longitude}")
    }
}