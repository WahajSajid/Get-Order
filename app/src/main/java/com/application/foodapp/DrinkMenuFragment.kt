package com.application.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentDrinkMenuBinding
import kotlinx.coroutines.sync.Mutex

class DrinkMenuFragment : Fragment() {
    private lateinit var binding:FragmentDrinkMenuBinding
    private lateinit var drinkItems:ArrayList<DrinksItemsData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_drink_menu, container, false)

        //Implemented logic to set up recycler view adapter
        val drinksItemsRecyclerView = binding.drinksRecyclerView
        val adapter = DrinksMenuAdapter()
        val layout = GridLayoutManager(context,2)
        drinksItemsRecyclerView.layoutManager = layout
        drinksItemsRecyclerView.adapter = adapter

        drinkItems = ArrayList()


        //Setting up onClick listener for add item button in the recycler view item using interface
        adapter.itemClickListener(object :DrinksMenuAdapter.OnItemClickListener{
            override fun addItemClickListener(quantityTextView: TextView, drinkNameTextView: TextView, position:Int) {
                val quantity = quantityTextView.text.toString().toInt()
                val foodName = drinkNameTextView.text.toString()
                val drinkItemsData = DrinksItemsData(foodName,quantity)
                drinkItems.add(drinkItemsData)
                Toast.makeText(context,"Item Added",Toast.LENGTH_SHORT).show()
            }
            override val mutex: Mutex = Mutex()
        })


        return binding.root
    }

}