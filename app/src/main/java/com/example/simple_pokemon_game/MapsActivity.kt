package com.example.simple_pokemon_game

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
        LoadPokemon()
    }

    var ACCESSLOCATION=123;
    fun checkPermission(){

        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESSLOCATION)
            return
        }

        GetUserLocation()
    }



    fun GetUserLocation(){
        Toast.makeText(this, "User location access on", Toast.LENGTH_LONG).show()
        //TODO: will implement later

        var myLocation = MyLocationListener()

        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f, myLocation)
            var myThread=myThread()
            myThread.start()
        }else{
            Toast.makeText(this, "permission not granted", Toast.LENGTH_LONG)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            ACCESSLOCATION->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    GetUserLocation()
                }else{
                    Toast.makeText(this, "We cannot access your location", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }

    var location:Location?=null

    inner class MyLocationListener:LocationListener{


        constructor(){
            location= Location("Start")
            location!!.longitude=0.0
            location!!.latitude=0.0
        }
        override fun onLocationChanged(p0: Location?) {
            location=p0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    inner class myThread:Thread{

        constructor():super(){

        }

        override fun run() {
            while (true){
                try{

                    runOnUiThread{
                        mMap!!.clear()
                        // Add a marker in Sydney and move the camera
                        val sydney = LatLng(location!!.latitude, location!!.longitude)
                        println("this are the values"+location!!.latitude.toString()+"\n"+location!!.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(sydney)
                            .title("Me")
                            .snippet("here is my location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario3)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
                    }

                    Thread.sleep(1000)

                }catch (ex:Exception){

                }
            }
        }
    }

    var listPokemons = ArrayList<Pokemon>()

    fun LoadPokemon(){
        listPokemons.add(Pokemon((R.drawable.charmander),  "Charmander", "stays at wuse", 55.0,9.062811, 7.470844))
        listPokemons.add(Pokemon((R.drawable.bulbasaur),  "bulbasaur", "crusing at maitama", 30.0,9.086602, 7.494941))
        listPokemons.add(Pokemon((R.drawable.squirtle),  "squirtle", "playing in central area", 67.0,9.052968, 7.478736))
//        listPokemons.add(Pokemon((R.drawable.charmander),  "Charmander", "here is from wuse", 55.0,9.062811, 7.470844))
    }
}
