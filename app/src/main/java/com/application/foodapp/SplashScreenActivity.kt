package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivitySplashScreenBinding
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

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
        Toast.makeText(this,"$storedPassword  $storedUserName",Toast.LENGTH_SHORT).show()

        val startTime = System.currentTimeMillis()
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Employees").child(storedUserName)
        if(NetworkUtil.isNetworkAvailable(this)){
            if(doesInternetHaveNetwork()){
                databaseReference.get().addOnSuccessListener {
                    val password = it.child("Password").value.toString()
                    val endTime = System.currentTimeMillis()
                    val delayTime = endTime - startTime
                    Handler().postDelayed({
                        if(password == storedPassword){
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        } else {
                            sharedPreferences.edit().remove("password").apply()
                            sharedPreferences.edit().remove("user_name").apply()
                            startActivity(Intent(this,UserLoginActivity::class.java))
                            finish()
                        }
                    },delayTime)
                }
            } else{
                Handler().postDelayed({
                    Toast.makeText(this,"Slow or unstable Internet connection",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,UserLoginActivity::class.java))
                    finish()
                },6000L)
            }
        } else{
            Handler().postDelayed({
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,UserLoginActivity::class.java))
                finish()
            },3000L)
        }

    }
    private fun doesInternetHaveNetwork(): Boolean {
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg params: Void?): Boolean {
                try {
                    val socket = Socket()
                    socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                    socket.close()
                    return true
                } catch (e: IOException) {
                    return false
                }
            }
        }
        return task.execute().get()
    }

}