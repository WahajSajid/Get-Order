package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentEditOrderBinding

class EditOrderFragment : Fragment() {

    private lateinit var binding:FragmentEditOrderBinding

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
      binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_order, container, false)

        //Creating an instance of MyApp Class to retrieve the data
        val myApp = requireActivity().application as MyApp

        //Retrieving foodItems from the MyApp class and setting up the recycler view
        val orderItems = ArrayList<OrderItems>()
        val foodItems = myApp.foodItems

        //Navigating back to the previous fragment when user clicks on  save changes.
        binding.saveChangesButton.setOnClickListener {
            val navController = findNavController()
            navController.popBackStack()
        }

            orderItems.addAll(foodItems)



        val recyclerView = binding.editOrderRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = EditOrderAdapter(myApp,orderItems){
            myApp.foodItems.remove(it)
        }
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


        return binding.root
    }
}