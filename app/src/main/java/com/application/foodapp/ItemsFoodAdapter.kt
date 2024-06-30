package com.application.foodapp

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ItemsFoodAdapter (private var items:List<Items>):RecyclerView.Adapter<ItemsFoodAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val availability = itemView.findViewById<TextView>(R.id.availability)!!
        val price = itemView.findViewById<TextView>(R.id.price)!!
        val name = itemView.findViewById<TextView>(R.id.foodName)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_menu_items,parent,false)
        return ViewHolder(view)
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


        if(item.Availability) {
            holder.availability.setTextColor(R.color.green_color)
            holder.availability.text = "Yes"
        }
        else {
            holder.availability.setTextColor(R.color.red_color)
            holder.availability.text = "No"
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Items>) {
        items = newItems
        notifyDataSetChanged()
    }
}