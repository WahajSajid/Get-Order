package com.application.foodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivityConfirmOrderBinding

@Suppress("DEPRECATION")
class ConfirmOrderActivity : AppCompatActivity() {
    private lateinit var binding :ActivityConfirmOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_order)
        val toolBar = binding.myToolBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Confirm Order"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}