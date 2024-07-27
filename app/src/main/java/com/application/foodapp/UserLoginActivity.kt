package com.application.foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.application.foodapp.databinding.ActivityUserLoginAcitvityBinding
import com.google.firebase.database.FirebaseDatabase

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserLoginAcitvityBinding
    private lateinit var signInDialogBox: SigningInDialogBox
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login_acitvity)

        initActivity()
    }

    private fun initActivity() {
        signInDialogBox = SigningInDialogBox()
        fragmentManager = supportFragmentManager
        binding.loginButton.setOnClickListener {
            if (isEmptyOrNot()) {
                if (NetworkUtil.isNetworkAvailable(this)) {
                    showDialog()
                    HasInternetAccess.hasInternetAccess(object : HasInternetAccessCallback {
                        override fun onInternetAccessAvailable() {
                            runOnUiThread {
                                authenticateEmployee()
                            }
                        }
                        override fun onInternetAccessNotAvailable() {
                            runOnUiThread {
                                Toast.makeText(this@UserLoginActivity,"Connection Timeout",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })


                } else Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Method to Authenticate the Employee
    private fun authenticateEmployee() {
        val sharedPreferences = getSharedPreferences("user_credential", MODE_PRIVATE)
        val userNameEnteredByTheUser = binding.enterUsernameEditText.text.toString()
        val enteredPassword = binding.enterPasswordEditText.text.toString()
        val database = FirebaseDatabase.getInstance()

        if (userNameEnteredByTheUser.contains(".") || userNameEnteredByTheUser.contains("#") || userNameEnteredByTheUser.contains(
                "$"
            ) || userNameEnteredByTheUser.contains("[") || userNameEnteredByTheUser.contains("]")
        ) {
            Toast.makeText(this, "Invalid UserName", Toast.LENGTH_SHORT).show()
            dismissDialog()
        } else {
            val databaseReference =
                database.getReference("Employees").child(userNameEnteredByTheUser.trim())
            databaseReference.get().addOnSuccessListener {
                val password = it.child("Password").value.toString()
                if (enteredPassword == password) {
                    sharedPreferences.edit().putString("password", password).apply()
                    sharedPreferences.edit().putString("user_name", userNameEnteredByTheUser.trim())
                        .apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    dismissDialog()
                    finish()
                } else {
                    dismissDialog()
                    Toast.makeText(
                        this@UserLoginActivity,
                        "Invalid Username or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //Method to check if all the input fields are empty or not
    private fun isEmptyOrNot(): Boolean {
        var isTrueOrFalse = true
        val editTextView = arrayOf(binding.enterPasswordEditText, binding.enterUsernameEditText)
        for (i in editTextView.indices) {
            if (editTextView[i].text.toString().isEmpty()) {
                editTextView[i].error = editTextView[i].hint.toString() + " Cannot be empty"
                isTrueOrFalse = false
            } else {
                editTextView[i].error = null
            }
        }
        return isTrueOrFalse
    }


    private fun showDialog() {
        signInDialogBox.show(fragmentManager, "signing in")
    }

    private fun dismissDialog() {
        signInDialogBox.dismiss()
    }

}

