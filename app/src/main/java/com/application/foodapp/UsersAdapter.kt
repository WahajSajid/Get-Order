package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class UsersAdapter(private var matesList:MutableList<Users>,context:Context):RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.users_items,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return matesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = matesList[position]
        holder.userName.text = list.UserName
    }
}