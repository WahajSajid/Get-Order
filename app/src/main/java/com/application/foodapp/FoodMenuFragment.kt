package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.application.foodapp.databinding.FragmentFoodMenuBinding
import com.google.gson.Gson
import kotlinx.coroutines.sync.Mutex

class FoodMenuFragment : Fragment() {
    private lateinit var binding: FragmentFoodMenuBinding
    private lateinit var foodItems: ArrayList<FoodItemsData>
    private val viewModel:SharedViewModelForActivity by activityViewModels()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
      binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_food_menu, container, false)

        //Implemented logic to set up recycler view adapter
        val recycleView = binding.foodItemsRecyclerview
        val adapter = FoodMenuAdapter()
        val layout = GridLayoutManager(context,2)
        recycleView.layoutManager = layout
        recycleView.adapter = adapter

        foodItems = ArrayList()
        //Setting up onClick listener for add item button in the recycler view item using interface
        adapter.itemClickListener(object :FoodMenuAdapter.OnItemClickListener{
            override fun addItemClickListener(quantityTextView: TextView, foodNameTextView: TextView,position:Int) {
                val quantity = quantityTextView.text.toString().toInt()
                val foodName = foodNameTextView.text.toString()
                val foodItemsData = FoodItemsData(foodName,quantity)
//               viewModel.foodItems.add(foodItemsData)
                foodItems.add(foodItemsData)
                Toast.makeText(context,"Item Added",Toast.LENGTH_SHORT).show()
                Toast.makeText(context,"${foodItems.size} class",Toast.LENGTH_SHORT).show()

            }
            override val mutex: Mutex = Mutex()
        })

        val app = requireActivity().application as MyApp
        app.foodItems = foodItems

//        // Convert the list to a JSON string
//        val gson = Gson()
//        val json = gson.toJson(foodItems)
//
//        // Save the JSON string in Shared Preferences
//        val sharedPreferences = activity?.getSharedPreferences("order_items", Context.MODE_PRIVATE)
//        with(sharedPreferences?.edit()) {
//            this?.putString("food_items", json)
//            this?.apply()
//        }


        return binding.root
    }

}