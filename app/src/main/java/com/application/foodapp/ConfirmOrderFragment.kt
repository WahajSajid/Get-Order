package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding: FragmentConfirmOrderBinding
    private val viewModel: SharedViewModelForActivity by activityViewModels()

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false)

        //Setting up the layout Manager and adapter for drinksOrderItems Recycler View
        val drinksOrderItemsRecyclerView = binding.drinksOrderItemsRecyclerview
        drinksOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        drinksOrderItemsRecyclerView.adapter = DrinksOrderItemsAdapter()


        val app = requireActivity().application as MyApp
       val foodItems =  app.foodItems

            //Setting up the layout Manager and adapter for food OrderItems Recycler View
            Toast.makeText(context, foodItems?.size.toString(), Toast.LENGTH_SHORT).show()
            val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
            foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
            val foodAdapter = FoodOrderItemsAdapter(foodItems!!)
            foodOrderItemsRecyclerView.adapter = foodAdapter
            foodAdapter.notifyDataSetChanged()



        return binding.root
    }
}