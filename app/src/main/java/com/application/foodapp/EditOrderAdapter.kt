package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditOrderAdapter(
    private val app:MyApp,
    private val orderItems: ArrayList<OrderItems>,
    private val onDeleteItem: (OrderItems) -> Unit,
) : RecyclerView.Adapter<EditOrderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plusButton: ImageView = itemView.findViewById(R.id.plusEditImageButton)
        val minusButton: ImageView = itemView.findViewById(R.id.minusEditImageButton)
        val quantityTextView: TextView = itemView.findViewById(R.id.editOrderItemQuantity)
        val orderItemName: TextView = itemView.findViewById(R.id.editOrderItemName)
        val deleteItem: ImageView = itemView.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.edit_order_items, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return orderItems.size
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = orderItems[position]
        holder.quantityTextView.text = items.quantity.toString()
        holder.orderItemName.text = items.item.name

        //Setting up onClickListener for deleteItemButton
        holder.deleteItem.setOnClickListener {
            orderItems.remove(items)
            app.newItemAdded = false
            notifyDataSetChanged()
            onDeleteItem(items)
        }


        //Setting up onClick Listeners for plusButton and minusButton to add and subtract the quantity of the order items.
        holder.plusButton.setOnClickListener {
            app.orderChanged = true
            val quantity = holder.quantityTextView.text.toString().toInt() + 1
            holder.quantityTextView.text = quantity.toString()
            items.quantity = quantity
            notifyDataSetChanged()
        }
        holder.minusButton.setOnClickListener {
            app.orderChanged = true
            if (holder.quantityTextView.text.toString().toInt() > 1) {
                val quantity = holder.quantityTextView.text.toString().toInt() - 1
                holder.quantityTextView.text = quantity.toString()
                items.quantity = quantity
                notifyDataSetChanged()
            }
        }
    }
}