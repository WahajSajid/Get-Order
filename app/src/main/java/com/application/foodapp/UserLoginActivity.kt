package com.application.foodapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.application.foodapp.databinding.ActivityUserLoginAcitvityBinding
import com.google.firebase.database.FirebaseDatabase

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserLoginAcitvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login_acitvity)

        binding.loginButton.setOnClickListener {
            if (isEmptyOrNot()) {
                if(NetworkUtil.isNetworkAvailable(this)){
                    binding.spinnerLayout.visibility = View.VISIBLE
                    authenticateEmployee()
                } else{
                    binding.spinnerLayout.visibility = View.GONE
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Method to Authenticate the Employee
    private fun authenticateEmployee() {
        val username = binding.enterUsernameEditText.text.toString()
        val enteredPassword = binding.enterpasswordEditText.text.toString()
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Employees").child(username.trim())
        databaseReference.get().addOnSuccessListener {
            val password = it.child("Password").value.toString()
            if (enteredPassword == password) {
                startActivity(Intent(this, MainActivity::class.java))
                binding.spinnerLayout.visibility = View.GONE
            } else {
                binding.spinnerLayout.visibility = View.GONE
                Toast.makeText(
                    this@UserLoginActivity,
                    "Invalid Username or Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Method to check if all the input fields are empty or not
    private fun isEmptyOrNot(): Boolean {
        var isTrueOrFalse = true
        val editTextInputLayout =
            arrayOf(binding.enterPasswordInputLayout, binding.enterUsernameInputLayout)
        val editTextView = arrayOf(binding.enterpasswordEditText, binding.enterUsernameEditText)
        for (i in editTextView.indices) {
            if (editTextView[i].text.toString().isEmpty()) {
                editTextInputLayout[i].error = editTextView[i].hint.toString() + " Cannot be empty"
                isTrueOrFalse = false
            } else {
                editTextInputLayout[i].error = null
            }
        }
        return isTrueOrFalse
    }


}

