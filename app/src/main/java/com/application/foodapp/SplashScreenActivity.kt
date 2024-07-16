package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivitySplashScreenBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        //Showing a splash screen and retrieving login Information from database making a delay till the data form the firebase is not retrieved
        val sharedPreferences = getSharedPreferences("user_credential", MODE_PRIVATE)
        val defaultValue = "default"
        val storedPassword = sharedPreferences.getString("password",defaultValue)!!
        val storedUserName = sharedPreferences.getString("user_name",defaultValue)!!
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Employees").child(storedUserName)
        if(NetworkUtil.isNetworkAvailable(this)){
            HasInternetAccess.hasInternetAccess(object :HasInternetAccessCallback{
                override fun onInternetAccessAvailable() {
                    runOnUiThread {
                        Toast.makeText(this@SplashScreenActivity,"Successful",Toast.LENGTH_SHORT).show()
                        success(storedPassword,databaseReference,sharedPreferences)
                    }
                }

                override fun onInternetAccessNotAvailable() {
                        failure()
                }
            })

        } else{
            Handler().postDelayed({
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,UserLoginActivity::class.java))
                finish()
            },3000L)
        }

    }
    private fun success(storedPassword:String,databaseReference:DatabaseReference,sharedPreferences:SharedPreferences){
        databaseReference.get().addOnSuccessListener {
            val password = it.child("Password").value.toString()
                if(password == storedPassword){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else {
                    sharedPreferences.edit().remove("password").apply()
                    sharedPreferences.edit().remove("user_name").apply()
                    startActivity(Intent(this,UserLoginActivity::class.java))
                    finish()
                }
        }
        databaseReference.get().addOnFailureListener { Toast.makeText(this@SplashScreenActivity,it.message,Toast.LENGTH_SHORT).show() }
    }
    private fun failure(){
        runOnUiThread {
            Toast.makeText(this, "Connection Timeout", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()
        }
    }

}