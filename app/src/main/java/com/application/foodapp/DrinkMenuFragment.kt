package com.application.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentDrinkMenuBinding

class DrinkMenuFragment : Fragment() {
    private lateinit var binding:FragmentDrinkMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_drink_menu, container, false)
        val drinksItemsRecyclerView = binding.drinksRecyclerView
        val adapter = DrinksMenuAdapter()
        val layout = GridLayoutManager(context,2)
        drinksItemsRecyclerView.layoutManager = layout
        drinksItemsRecyclerView.adapter = adapter


        return binding.root
    }

}