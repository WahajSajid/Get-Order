package com.application.foodapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.application.foodapp.databinding.ActivityConfirmOrderBinding

@Suppress("DEPRECATION")
class ConfirmOrderActivity : AppCompatActivity() {
    private lateinit var binding :ActivityConfirmOrderBinding
    private lateinit var navController:NavController
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_order)

        //Retrieving table name from intent
        val tableName = intent.getStringExtra("tableName")

        sharedViewModel.tableName.value = tableName

        val toolBar = binding.myToolBar
        setSupportActionBar(toolBar)
        val navigationHost = supportFragmentManager.findFragmentById(R.id.NavigationHost) as NavHostFragment
        navController = navigationHost.navController
        val appBarConfiguration = AppBarConfiguration(
            fallbackOnNavigateUpListener = ::onSupportNavigateUp,
            topLevelDestinationIds = (setOf(R.id.confirmOrderFragment))
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        toolBar.setupWithNavController(navController,appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            supportActionBar?.title = destination.label
        }
        setupBackButton()
    }


    //Method to set up the action bar with back stack of activity and fragments
    private fun setupBackButton() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(destination.id == R.id.confirmOrderFragment || destination.id == R.id.editOrderFragment)
            supportActionBar?.title = destination.label
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}