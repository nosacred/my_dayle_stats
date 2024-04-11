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





        var allOrder = emptyList<Order>()
        var salesToday = emptyList<Sale>()
        var stocksToday = emptyList<Stock>()

        val date = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val apiKey =
            "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTcxNzU3NjE4NSwiaWQiOiIxMDMzNTczNS0wNTViLTQ3NWQtOGQ2OC1mMTA1ZDdkMWFkYTgiLCJpaWQiOjkxNDU0MTMsIm9pZCI6MTM1NTI3LCJzIjozMiwic2lkIjoiZTI2NWVlZmEtYjY1My00OTlkLThmMTYtMjAzYzJmNjMwNGQ1IiwidWlkIjo5MTQ1NDEzfQ.omZjk0y5UhP4lDTeY6CP47acpmQY6cD8QpPZRF7vJ0f1KfYz6JEo7ahEGKfVYF2DIFzqmMcc_Rro5RUrpN1YAw"



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(conf)|| super.onSupportNavigateUp()
    }


}