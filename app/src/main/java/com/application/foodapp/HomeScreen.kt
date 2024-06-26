package com.application.foodapp

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
import kotlinx.coroutines.sync.Mutex

class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val  adapter = TablesAdapter()
        recyclerView.adapter = adapter


        adapter.itemClickListener(object : TablesAdapter.OnItemClickListener{
            override fun getOrderButtonClickListener(tableNo: TextView) {
                sharedViewModel.tableNo.value = tableNo.text.toString()
                val intent = Intent(context,GetOrderActivity::class.java).apply {
                    putExtra("tableNo",tableNo.text.toString())
                }
                startActivity(intent)
            }

            override val mutex: Mutex
                get() = Mutex()
        })




        return binding.root
    }

}