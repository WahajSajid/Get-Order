package com.application.foodapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val alertDialog = AlertDialog.Builder(this)
        val sharedPreferences = getSharedPreferences("user_credential", MODE_PRIVATE)
        binding.logoutButton.setOnClickListener {
            alertDialog.setTitle("Logout")
            alertDialog.setMessage("Are you sure you want to logout?")
            alertDialog.setPositiveButton("Yes") { _, _ ->
                sharedPreferences.edit()?.putString("password", "default")?.apply()
                sharedPreferences.edit()?.putString("user_name","default")?.apply()
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            }
            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }
}