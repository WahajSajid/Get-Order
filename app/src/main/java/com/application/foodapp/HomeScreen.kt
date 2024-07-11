package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.foodapp.databinding.FragmentHomeScreenBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.sync.Mutex

class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var tables: ArrayList<Tables>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)

        call()

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Tables")
        // Initialize matesList
        tables = ArrayList()

        //Showing the progress bar until the table list are shown
        binding.spinnerLayout.visibility = View.VISIBLE

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TablesAdapter(tables, requireContext())
        recyclerView.adapter = adapter


        adapter.itemClickListener(object : TablesAdapter.OnItemClickListener {
            override fun getOrderButtonClickListener(tableName: TextView) {
                sharedViewModel.tableName.value = tableName.text.toString()
                val intent = Intent(context, GetOrderActivity::class.java).apply {
                    putExtra("tableName", tableName.text.toString())
                }
                startActivity(intent)
            }

            override val mutex: Mutex
                get() = Mutex()
        })




                databaseReference.addValueEventListener(object : ValueEventListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()) binding.noTablesAddedYetLayout.visibility = View.GONE
                        else binding.noTablesAddedYetLayout.visibility = View.VISIBLE

                        binding.spinnerLayout.visibility = View.GONE
                        tables.clear()
                        for (data in snapshot.children) {
                            val mateInfo = data.getValue(Tables::class.java)
                            if (mateInfo != null) {
                                tables.add(mateInfo)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                })



        return binding.root
    }
    private fun  call(){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Employees").child("haamza")
            databaseReference.child("UserName").addValueEventListener(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    Toast.makeText(context,"Username changed",Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        databaseReference.child("Password").addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(context,"Password Changed",Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}