package com.shahnoza.realtimegooglemap

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.shahnoza.realtimegooglemap.databinding.ActivityMapBinding







const val TAG="Map"
class Map : AppCompatActivity(), OnMapReadyCallback {

    private  var mMap: GoogleMap?=null
    private lateinit var binding: ActivityMapBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = com.google.android.gms.location.LocationRequest.create()

        // qancha vaqtda real time joylashuvni belgilab turishi
        locationRequest.setInterval(1000)
        locationRequest.setFastestInterval(1000)
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.getMainLooper())



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    var marker:Marker?=null

    val locationCallBack=object :LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            if (null==p0){
                return
            }
            for (location:Location in p0.locations) {
                Log.d(TAG, "onLocationResult: $location")
                if (mMap!=null){
                    val polyline=mMap?.addPolyline(
                        PolylineOptions()
                            .clickable(true)
                            .add(LatLng(location.latitude,location.longitude))
                    )

                    val cameraPosition=CameraPosition.Builder().target(LatLng(location.latitude,location.longitude)).bearing(location.bearing).zoom(10F).build()

                    mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    if (marker==null){
                        marker = mMap?.addMarker(MarkerOptions().title("Markerim").position(LatLng(location.latitude,location.longitude)))
                    }else{
                        marker?.position= LatLng(location.latitude,location.longitude)
                    }

                }
            }
        }


    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.mapType=GoogleMap.MAP_TYPE_HYBRID





    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }
}