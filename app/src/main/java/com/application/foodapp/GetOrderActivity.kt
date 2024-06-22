package com.application.foodapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivityGetOrderBinding
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION", "NAME_SHADOWING")
class GetOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetOrderBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_get_order)


        //Retrieving data from the intent and setting it to the text view
        val intent = intent
        val tableNo = intent.getStringExtra("tableNo")
        binding.tableNo.text = tableNo


        //Setting up on click listener for imageButton on the toolbar
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, ConfirmOrderActivity::class.java)
            startActivity(intent)
        }


        //Setting up toolbar action button
        val toolBar = binding.toolBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Get Order for Table $tableNo"


        //Setting up tabLayout and viewPager
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        //Creating object of FragmentStateAdapter to handle fragments
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        //Attaching tab layout with view pager on run time
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Drinks Menu"
                1 -> "Food Menu"
                else -> ""
            }
        }.attach()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}