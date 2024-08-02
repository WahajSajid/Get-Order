package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderItemsAdapter(private var orderItemsData:ArrayList<OrderItems>):RecyclerView.Adapter<OrderItemsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foodNameTextView = itemView.findViewById<TextView>(R.id.orderItemName)!!
        val quantity = itemView.findViewById<TextView>(R.id.orderItemQuantity)!!

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_items,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
       return orderItemsData.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = orderItemsData[position]
        holder.foodNameTextView.text = orderItem.item.name
        holder.quantity.text = orderItem.quantity.toString()
    }
}