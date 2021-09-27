package com.example.aop_part5_chapter05.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.aop_part5_chapter05.R
import com.example.aop_part5_chapter05.databinding.ActivityMainBinding
import com.example.aop_part5_chapter05.domain.Station
import com.example.aop_part5_chapter05.extensions.toGone
import com.example.aop_part5_chapter05.extensions.toVisible
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initViews() {
        // setSupportActionBar(binding.toolbar) // 왜 오류뜨지
        setupActionBarWithNavController(navigationController)
    }

    private fun bindViews() {
        navigationController.addOnDestinationChangedListener { _, destination, argument ->
            if (destination.id == R.id.station_arrivals_dest) {
               // title = StationArrivalFragmentArgs.fromBundle(argument!!).station.name
                binding.toolbar.toVisible()
            } else {
                binding.toolbar.toGone()
            }
        }
    }
}