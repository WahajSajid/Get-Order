package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.foodapp.databinding.FragmentConfirmOrderBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfirmOrderFragment : Fragment() {
    private lateinit var binding: FragmentConfirmOrderBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var orderConfirmationDialog: OrderConfirmationDialog
    private lateinit var fragmentManager: FragmentManager
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var orderData: ArrayList<OrderItems>
    private lateinit var databaseReferenceOrdered: DatabaseReference
    private lateinit var isTableBusyReference:DatabaseReference
    private lateinit var orderChangedReference :DatabaseReference
    private lateinit var app: MyApp

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false)


        //Initializing orderConfirmationDialog and fragment manager
        orderConfirmationDialog = OrderConfirmationDialog()
        fragmentManager = childFragmentManager

        orderData = ArrayList()
        //Retrieving table name from the sharedViewModel
        val tableName = sharedViewModel.tableName.value.toString()

        //Creating instance of MyApp class to retrieve the data shared from the FoodMenuFragment and DrinkMenuFragment
        app = requireActivity().application as MyApp
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReferenceOrdered =
            firebaseDatabase.getReference("Tables").child(tableName).child("ordered")

        isTableBusyReference =
            firebaseDatabase.getReference("Tables").child(tableName).child("isTableBusy")

        orderChangedReference = firebaseDatabase.getReference("Tables").child(tableName).child("orderChanged")
        //Retrieving foodItems from the MyApp class and setting up the recycler view
        val orderItems = ArrayList<OrderItems>()
        val foodItems = app.foodItems

        orderItems.addAll(foodItems)

        //Adapter for orderItems
        val foodAdapter = OrderItemsAdapter(orderItems)

        //Checking if the user wants to cancel the order or not.
        if (app.cancelOrderOrNot) {
            orderItems.clear()
            app.foodItems.clear()
            app.cancelOrderOrNot = false
            foodAdapter.notifyDataSetChanged()
            binding.viewLayout.visibility = View.GONE
            binding.nothingToShowTextView.visibility = View.VISIBLE
        }
        if (foodItems.isEmpty()) {
            binding.viewLayout.visibility = View.GONE
            binding.nothingToShowTextView.visibility = View.VISIBLE
        } else {
            binding.viewLayout.visibility = View.VISIBLE
            binding.nothingToShowTextView.visibility = View.GONE
            val foodOrderItemsRecyclerView = binding.foodOrderItemsRecyclerview
            foodOrderItemsRecyclerView.layoutManager = LinearLayoutManager(context)
            foodOrderItemsRecyclerView.adapter = foodAdapter
            foodAdapter.notifyDataSetChanged()
        }
        //Setting up onClickListener for confirmOrderButton
        binding.confirmOrderButton.setOnClickListener {
            orderConfirmationDialog.show(fragmentManager, "Confirming order...")
            if (NetworkUtil.isNetworkAvailable(requireContext())) {
                HasInternetAccess.hasInternetAccess(object : HasInternetAccessCallback {
                    override fun onInternetAccessAvailable() {
                        requireActivity().runOnUiThread {
                            confirmOrder(tableName, orderItems)
                        }
                    }

                    override fun onInternetAccessNotAvailable() {
                        requireActivity().runOnUiThread {
                            Toast.makeText(context, "Connection Timeout", Toast.LENGTH_SHORT).show()
                            orderConfirmationDialog.dismiss()
                        }
                    }
                })
            } else {
                Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show()
                orderConfirmationDialog.dismiss()
            }
        }

        //Setting up onClick Listener for cancel button
        binding.cancelOrderButton.setOnClickListener {
            val popScreen = CancelOrderDialogFragment()
            popScreen.show(childFragmentManager, "cancel_order_fragment")
        }


        binding.editOrderButton.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_confirmOrderFragment_to_editOrderFragment)
        }


        return binding.root
    }

    private fun confirmOrder(tableName: String, orderItems: ArrayList<OrderItems>) {
        val database = FirebaseDatabase.getInstance()
        val databaseReference =
            database.getReference().child("Tables").child(tableName).child("Orders")
        databaseReference.setValue(orderItems)
            .addOnSuccessListener {
                if (app.newItemAdded) {
                    databaseReferenceOrdered.setValue(true)
                    isTableBusyReference.setValue(true)
                    app.newItemAdded = false
                }
                if(app.orderChanged){
                    orderChangedReference.setValue(true)
                    isTableBusyReference.setValue(true)
                    app.orderChanged = false
                }
                Toast.makeText(context, "Order placed", Toast.LENGTH_SHORT).show()
                orderConfirmationDialog.dismiss()
                val intent = Intent(requireActivity(),MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                requireActivity().finish()
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }
}