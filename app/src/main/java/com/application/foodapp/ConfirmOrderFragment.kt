package com.application.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding:FragmentConfirmOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_confirm_order, container, false)

        //Setting up the layout Manager and adapter for drinksOrderItems Recycler View
        val drinksOrderItemsRecyclerView = binding.drinksOrderItemsRecyclerview
        drinksOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        drinksOrderItemsRecyclerView.adapter = DrinksOrderItemsAdapter()


        //Setting up the layout Manager and adapter for food OrderItems Recycler View
        val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
        foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        foodOrderItemsRecyclerView.adapter = FoodOrderItemsAdapter()


        return binding.root
    }
}