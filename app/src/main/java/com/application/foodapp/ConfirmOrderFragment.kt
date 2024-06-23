package com.application.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding:FragmentConfirmOrderBinding
    private lateinit var viewModel:SharedViewModelForActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_confirm_order, container, false)

        //Setting up the layout Manager and adapter for drinksOrderItems Recycler View\
        val drinksOrderItemsRecyclerView = binding.drinksOrderItemsRecyclerview
        drinksOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        drinksOrderItemsRecyclerView.adapter = DrinksOrderItemsAdapter()


        viewModel =  ViewModelProvider(requireActivity()).get(SharedViewModelForActivity::class.java)
        viewModel.foodItems.observe(viewLifecycleOwner, Observer { foodItems ->
            // Use the retrieved data
            //Setting up the layout Manager and adapter for food OrderItems Recycler View
            if(foodItems.isNotEmpty()) {
                Toast.makeText(context, foodItems.size.toString(), Toast.LENGTH_SHORT).show()
                val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
                foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
                val foodAdapter = FoodOrderItemsAdapter(foodItems)
                foodOrderItemsRecyclerView.adapter = foodAdapter
            }else  Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
        })




        return binding.root
    }
}