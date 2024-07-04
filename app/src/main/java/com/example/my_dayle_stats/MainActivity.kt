package com.example.my_dayle_stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.my_dayle_stats.dao.Database
import com.example.my_dayle_stats.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var conf : AppBarConfiguration
    private lateinit var navController : NavController

    private val dataModel :DataModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Database.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainInclude.toolbar)
        navController= findNavController(R.id.fragmentContainerView)
        conf = AppBarConfiguration(
            setOf(
                R.id.introAPI,
                R.id.upDateFragment,
                R.id.tabFragment,
                R.id.monthStatsFragment,
                R.id.wereHousesFragment
            ), binding.drawer
        )
        setupActionBarWithNavController(navController,conf)
        binding.navView.setupWithNavController(navController)


        val botNav = findViewById<BottomNavigationView>(R.id.btnNavView)
        botNav.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(conf)|| super.onSupportNavigateUp()
    }


}