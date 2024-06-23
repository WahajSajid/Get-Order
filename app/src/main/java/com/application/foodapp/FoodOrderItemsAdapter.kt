package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class FoodOrderItemsAdapter(private var foodItemsData:ArrayList<FoodItemsData>):RecyclerView.Adapter<FoodOrderItemsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foodNameTextView = itemView.findViewById<TextView>(R.id.foodItem)
        val quantityTextView = itemView.findViewById<TextView>(R.id.quantityTextView)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_order_items,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
       return foodItemsData.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItemsData[position]
        holder.foodNameTextView.text = fooditem.foodItem
        holder.quantityTextView.text = fooditem.quanitiy.toString()
    }
}