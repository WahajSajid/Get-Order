//package com.application.foodapp
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.application.foodapp.databinding.ActivityUsersBinding
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class UsersActivity : AppCompatActivity() {
//    private lateinit var binding:ActivityUsersBinding
//    private lateinit var matesList: ArrayList<Tables>
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_users)
//
//
//        val database = FirebaseDatabase.getInstance()
//        val databaseReference = database.getReference("Employees")
//        // Initialize matesList
//        matesList = ArrayList()
//
//
//
//
//
//        // Track the last key processed to avoid duplicates
//        var lastKey: String? = null
//
//
//        // Recycler View initialization and adapter setting
//        val recyclerView = binding.usersRecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
////        val adapter = UsersAdapter(matesList,this)
//        recyclerView.adapter = adapter
//
//        databaseReference.addValueEventListener(object :ValueEventListener{
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                matesList.clear()
//                for(data in snapshot.children){
//                    val mateInfo = data.getValue(Tables::class.java)
//                    if (mateInfo != null) {
//                        matesList.add(mateInfo)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@UsersActivity,error.message,Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}