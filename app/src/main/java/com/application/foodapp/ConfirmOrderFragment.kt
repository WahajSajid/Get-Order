package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding: FragmentConfirmOrderBinding
    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false)


        //Creating instance of MyApp class to retrieve the data shared from the FoodMenuFragment and DrinkMenuFragment
        val app = requireActivity().application as MyApp

       //Retrieving foodItems from the MyApp class and setting up the recycler view
        val orderItems = ArrayList<OrderItems>()
        val foodItems = app.foodItems
        val drinkItems = app.drinkItems

        if(drinkItems != null){
            orderItems.addAll(drinkItems)
        }
        if(foodItems !=null) {
            orderItems.addAll(foodItems)
        }




        if(drinkItems.isNullOrEmpty() && foodItems.isNullOrEmpty()){
            binding.viewLayout.visibility = View.GONE
            binding.nothingToShowTextView.visibility = View.VISIBLE
        }
        else{
            binding.viewLayout.visibility = View.VISIBLE
            binding.nothingToShowTextView.visibility = View.GONE
            val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
            foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
            val foodAdapter = OrderItemsAdapter(orderItems)
            foodOrderItemsRecyclerView.adapter = foodAdapter
            foodAdapter.notifyDataSetChanged()
        }




        return binding.root
    }
}