package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class DrinksOrderItemsAdapter:RecyclerView.Adapter<DrinksOrderItemsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drink_order_items,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}