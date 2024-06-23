package com.application.foodapp

import android.annotation.SuppressLint
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
class DrinksMenuAdapter : RecyclerView.Adapter<DrinksMenuAdapter.ViewHolder>() {


    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {


        fun addItemClickListener(quantityTextView:TextView,drinkNameTextView:TextView,position: Int)
        val mutex: Mutex
    }

    fun itemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }


    class ViewHolder(itemView: View,clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val drinkNameTextView: TextView = itemView.findViewById(R.id.drinkName)
        val quantityTextView:TextView = itemView.findViewById(R.id.quantity)
        val minusButton: ImageView = itemView.findViewById(R.id.minusImageButton)
        val plusButton: ImageView = itemView.findViewById(R.id.plusImageButton)
        val addItemButton: Button = itemView.findViewById(R.id.addItemButton)


        init {
            addItemButton.setOnClickListener {
                addItemButton.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    clickListener.mutex.withLock {
                        clickListener.addItemClickListener(quantityTextView,drinkNameTextView,position)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drinks_menu_itmes, parent, false)
        return ViewHolder(view,clickListener)
    }


    override fun getItemCount(): Int = 6

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.drinkNameTextView.text = "Coca Cola"
            1 -> holder.drinkNameTextView.text = "Sprite"
            2 -> holder.drinkNameTextView.text = "Lemon Juice"
            3 -> holder.drinkNameTextView.text = "Pepsi"
            4 -> holder.drinkNameTextView.text = "Nestle Juice"
            5 -> holder.drinkNameTextView.text = "Marinda"
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