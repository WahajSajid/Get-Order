package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DrinksMenuAdapter : RecyclerView.Adapter<DrinksMenuAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkName: TextView = itemView.findViewById(R.id.drinkName)
        val quantityTextView:TextView = itemView.findViewById(R.id.quantity)
        val minusButton: ImageView = itemView.findViewById(R.id.minusImageButton)
        val plusButton: ImageView = itemView.findViewById(R.id.plusImageButton)
        val addItemButton: Button = itemView.findViewById(R.id.addItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drinks_menu_itmes, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = 6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.drinkName.text = "Coca Cola"
            1 -> holder.drinkName.text = "Sprite"
            2 -> holder.drinkName.text = "Lemon Juice"
            3 -> holder.drinkName.text = "Pepsi"
            4 -> holder.drinkName.text = "Nestle Juice"
            5 -> holder.drinkName.text = "Marinda"
//            6 -> holder.drinkName.text = "Cola Next"
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
}