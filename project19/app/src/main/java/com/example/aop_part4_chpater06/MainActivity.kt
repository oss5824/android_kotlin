package com.example.aop_part4_chpater06

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.aop_part4_chpater06.data.data.Repository
import com.example.aop_part4_chpater06.data.data.models.airquaility.Grade
import com.example.aop_part4_chpater06.data.data.models.airquaility.MeasuredValue
import com.example.aop_part4_chpater06.data.data.models.monitoringstation.MonitoringStation
import com.example.aop_part4_chpater06.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initVariables()
        requestLocationPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
        scope.cancel()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (!locationPermissionGranted) {
            finish()
        } else {
            fetchAirQualityData()
        }

    }

    private fun initVariables() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @SuppressLint("MissingPermission")
    private fun fetchAirQualityData() {
        Log.d("?????? ?????????", " ")
        cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener { location ->
            scope.launch {
                val monitoringStation = Repository.getNearbyMonitoringStation(37.toDouble(), 37.toDouble())

                val measuredValue = Repository.getLatestAirQualityData(monitoringStation!!.stationName!!)

            }
        }.addOnFailureListener {
            Log.d("??????", it.toString())
        }
    }

    @SuppressLint("ResourceAsColor")
    fun displayAiQualityData(monitoringStation: MonitoringStation, measuredValue: MeasuredValue){
        binding.measuringStationNameTextView.text=monitoringStation.stationName
        binding.measuringStationAddressTextView.text=monitoringStation.addr

        (measuredValue.khaiGrade?: Grade.UNKNOWN).let{grade->
            binding.root.setBackgroundColor(grade.colorResId)
            binding.totalGradeLabelTextView.text=grade.label
            binding.totalGradleEmojiTextView.text=grade.emoji
        }

        with(measuredValue){
        }
    }

    companion object {
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
    }
}