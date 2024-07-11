package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding
import com.google.firebase.database.FirebaseDatabase

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding: FragmentConfirmOrderBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()
    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false)

        //Retrieving table name from the sharedViewModel
        val tableName = sharedViewModel.tableName.value.toString()

        //Creating instance of MyApp class to retrieve the data shared from the FoodMenuFragment and DrinkMenuFragment
        val app = requireActivity().application as MyApp

       //Retrieving foodItems from the MyApp class and setting up the recycler view
        val orderItems = ArrayList<OrderItems>()
        val foodItems = app.foodItems
        val drinkItems = app.drinkItems

//        if(drinkItems != null){
//            orderItems.addAll(drinkItems)
//        }
        if(foodItems !=null) {
            orderItems.addAll(foodItems)
        }

        //Adapter for orderItems
        val foodAdapter = OrderItemsAdapter(orderItems)

        //Checking if the user wants to cancel the order or not.
        if(app.cancelOrderOrNot){
            orderItems.clear()
            app.drinkItems?.clear()
            app.foodItems?.clear()
            app.cancelOrderOrNot = false
            foodAdapter.notifyDataSetChanged()
            binding.viewLayout.visibility = View.GONE
            binding.nothingToShowTextView.visibility = View.VISIBLE
        }


        if(drinkItems.isNullOrEmpty() && foodItems.isNullOrEmpty()){
            binding.viewLayout.visibility = View.GONE
            binding.nothingToShowTextView.visibility = View.VISIBLE
        }
        else{
            Toast.makeText(context,orderItems.size.toString(),Toast.LENGTH_SHORT).show()
            binding.viewLayout.visibility = View.VISIBLE
            binding.nothingToShowTextView.visibility = View.GONE
            val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
            foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
            foodOrderItemsRecyclerView.adapter = foodAdapter
            foodAdapter.notifyDataSetChanged()
        }


        //Setting up onClickListener for confirmOrderButton
        binding.confirmOrderButton.setOnClickListener {
            confirmOrder(tableName,orderItems)
        }

        //Setting up onClick Listener for cancel button
        binding.cancelOrderButton.setOnClickListener{
            val popScreen = CancelOrderDialogFragment()
            popScreen.show(childFragmentManager,"cancel_order_fragment")
        }


        binding.editOrderButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_confirmOrderFragment_to_editOrderFragment)
        }



        return binding.root
    }

    private fun confirmOrder(tableName:String,orderItems:ArrayList<OrderItems>){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference().child("Tables").child(tableName).child("Order")
        databaseReference.setValue(orderItems)
        Toast.makeText(context,tableName,Toast.LENGTH_SHORT).show()
    }
}