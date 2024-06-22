package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodMenuAdapter:RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foodName = itemView.findViewById<TextView>(R.id.foodName)
        val availability = itemView.findViewById<TextView>(R.id.availability)
        val plusButton = itemView.findViewById<ImageView>(R.id.plusImageButton)
        val minusButton = itemView.findViewById<ImageView>(R.id.minusImageButton)
        val addItemButton = itemView.findViewById<Button>(R.id.addItemButton)
        val quantityTextView = itemView.findViewById<TextView>(R.id.quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.food_menu_items,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(position) {
            0 -> holder.foodName.text = "Chicken Karahi"
            1 -> holder.foodName.text = "Chicken Tika"
            2 -> holder.foodName.text = "Mutton Karahi"
            3 -> holder.foodName.text = "Mutton Tika"
            4 -> holder.foodName.text = "Beef Karahi"
            5 -> holder.foodName.text = "Beef Tika"
        }

        holder.plusButton.setOnClickListener {
            val quantity = holder.quantityTextView.text.toString().toInt() + 1
            holder.quantityTextView.text = quantity.toString()
        }
        holder.minusButton.setOnClickListener {
            if(holder.quantityTextView.text.toString().toInt() > 1){
                val quantity = holder.quantityTextView.text.toString().toInt() - 1
                holder.quantityTextView.text = quantity.toString()
            }
        }
    }


    override fun getItemCount(): Int {
        return 6
    }
}