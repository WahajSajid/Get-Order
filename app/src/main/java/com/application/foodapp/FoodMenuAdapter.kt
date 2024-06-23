package com.application.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Suppress("DEPRECATION")
class FoodMenuAdapter:RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {

    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {


        fun addItemClickListener(quantityTextView:TextView,foodNameTextView:TextView,position: Int)
        val mutex: Mutex
    }

    fun itemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    class ViewHolder(itemView: View,clickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        val foodNameTextView = itemView.findViewById<TextView>(R.id.foodName)
        val availability = itemView.findViewById<TextView>(R.id.availability)
        val plusButton = itemView.findViewById<ImageView>(R.id.plusImageButton)
        val minusButton = itemView.findViewById<ImageView>(R.id.minusImageButton)
        val addItemButton = itemView.findViewById<Button>(R.id.addItemButton)
        val quantityTextView = itemView.findViewById<TextView>(R.id.quantity)


        init {
            addItemButton.setOnClickListener {
                addItemButton.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    clickListener.mutex.withLock {
                        clickListener.addItemClickListener(quantityTextView,foodNameTextView,position)
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.food_menu_items,parent,false)
        return ViewHolder(view,clickListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(position) {
            0 -> holder.foodNameTextView.text = "Chicken Karahi"
            1 -> holder.foodNameTextView.text = "Chicken Tika"
            2 -> holder.foodNameTextView.text = "Mutton Karahi"
            3 -> holder.foodNameTextView.text = "Mutton Tika"
            4 -> holder.foodNameTextView.text = "Beef Karahi"
            5 -> holder.foodNameTextView.text = "Beef Tika"
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