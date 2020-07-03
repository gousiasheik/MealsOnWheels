package com.mealsonwheels


import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mealsonwheels.ui.DashboardActivity

class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleMap.OnMarkerClickListener{

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val myPlace = LatLng(15.73, 76.77)  // this is New York
        map.addMarker(MarkerOptions().position(myPlace).title("Kurry Kingdom"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12.0f))

        val myPlace2 = LatLng(15.74, 76.73)  // this is New York
        map.addMarker(MarkerOptions().position(myPlace2).title("Indian Masala"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace2, 12.0f))

        val myPlace3 = LatLng(15.83, 76.70)  // this is New York
        map.addMarker(MarkerOptions().position(myPlace3).title("SpiceUp"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace3, 12.0f))

        val myPlace4 = LatLng(15.90,76.68)  // this is New York
        map.addMarker(MarkerOptions().position(myPlace4).title("Bawarchi Hyderabad"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace4, 12.0f))

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }
    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        // 2
        map.addMarker(markerOptions)
    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true
//u can change map type here
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {

        val intent= Intent(this,DashboardActivity::class.java)
        startActivity(intent)
        return false
    }





}


