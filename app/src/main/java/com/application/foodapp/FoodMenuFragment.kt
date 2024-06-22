package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.application.foodapp.databinding.FragmentFoodMenuBinding

class FoodMenuFragment : Fragment() {
    private lateinit var binding: FragmentFoodMenuBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
      binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_food_menu, container, false)
        val recycleView = binding.foodItemsRecyclerview
        val adapter = FoodMenuAdapter()
        val layout = GridLayoutManager(context,2)
        recycleView.layoutManager = layout
        recycleView.adapter = adapter

        return binding.root
    }

}