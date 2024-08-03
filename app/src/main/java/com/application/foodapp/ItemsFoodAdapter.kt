package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ItemsFoodAdapter(private var items: List<Items>, private var context: Context) :
    RecyclerView.Adapter<ItemsFoodAdapter.ViewHolder>() {


    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {


        fun addItemClickListener(
            availabilityTextView: TextView,
            nameTextView: TextView,
            priceTextView: TextView,
            quantityTextView: TextView,
            position: Int,
        )

        val mutex: Mutex
    }

    fun itemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }


    @Suppress("DEPRECATION")
    class ViewHolder(itemView: View, clickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val availability = itemView.findViewById<TextView>(R.id.availability)!!
        val price = itemView.findViewById<TextView>(R.id.price)!!
        val name = itemView.findViewById<TextView>(R.id.foodName)!!
        val plusButton = itemView.findViewById<ImageView>(R.id.plusImageButton)!!
        val minusButton = itemView.findViewById<ImageView>(R.id.minusImageButton)!!
        val quantity = itemView.findViewById<TextView>(R.id.quantity)!!
        val addItemButton = itemView.findViewById<Button>(R.id.addItemButton)!!


        init {
            addItemButton.setOnClickListener {
                addItemButton.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    clickListener.mutex.withLock {
                        clickListener.addItemClickListener(
                            availability,
                            name,
                            price,
                            quantity,
                            position
                        )
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.food_menu_items, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.Name
        holder.price.text = item.Price.toString()
        holder.availability.text = item.Availability.toString()


        if (item.Availability) {
            holder.availability.setTextColor(ContextCompat.getColor(context, R.color.green_color))
            holder.availability.text = "Yes"
        } else {
            holder.availability.setTextColor(ContextCompat.getColor(context, R.color.red_color))
            holder.availability.text = "No"
            holder.addItemButton.isClickable = false
            holder.addItemButton.setTextColor(ContextCompat.getColor(context,R.color.red_color))
        }

        holder.plusButton.setOnClickListener {
            val quantity = holder.quantity.text.toString().toInt() + 1
            holder.quantity.text = quantity.toString()
        }
        holder.minusButton.setOnClickListener {
            if (holder.quantity.text.toString().toInt() > 1) {
                val quantity = holder.quantity.text.toString().toInt() - 1
                holder.quantity.text = quantity.toString()
            }
        }

    }
}