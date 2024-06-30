package com.application.foodapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TablesAdapter(private val tables:MutableList<Tables>,context: Context) : RecyclerView.Adapter<TablesAdapter.ViewHolder>() {


    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {


        fun getOrderButtonClickListener(tableName:TextView)
        val mutex: Mutex
    }

    fun itemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }


    class ViewHolder(itemView: View,clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val dropIconButton: ImageView = itemView.findViewById(R.id.dropdownIcon)
        val getOrderButton: Button = itemView.findViewById(R.id.getOrderButton)
        val tableName: TextView = itemView.findViewById(R.id.tableNameTextView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        fun collapseExpandedView(){
            getOrderButton.visibility = View.GONE
        }

        init {
            getOrderButton.setOnClickListener {
                getOrderButton.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    clickListener.mutex.withLock {
                        clickListener.getOrderButtonClickListener(tableName)
                    }
                }
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tables_items, parent, false)
        return ViewHolder(view,clickListener)
    }


    override fun onBindViewHolder(holder: TablesAdapter.ViewHolder, position: Int) {
        val tablesList= tables[position]
        holder.tableName.text = tablesList.TableName

        val expand = ScaleAnimation(
            0f, 1.1f,
            1f, 1f,
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, 0f
        )
        expand.duration = 250

        val contract = ScaleAnimation(
            1.1f, 0f,
            1f, 1f,
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, 0f
        )
        contract.duration = 250





        holder.dropIconButton.setOnClickListener {
            isAnyItemExpanded(position)
            if (tablesList.isExpanded) {
                holder.cardView.startAnimation(expand)
                holder.getOrderButton.visibility = View.GONE
                holder.dropIconButton.setImageResource(R.drawable.baseline_arrow_drop_down_24)
                tablesList.isExpanded = false
            } else {
                holder.cardView.startAnimation(expand)
                holder.getOrderButton.visibility = View.VISIBLE
                holder.dropIconButton.setImageResource(R.drawable.baseline_arrow_drop_up_24)
                tablesList.isExpanded = true
            }
        }



        holder.cardView.setOnClickListener {
            isAnyItemExpanded(position)
            if (tablesList.isExpanded) {
                holder.cardView.startAnimation(expand)
                holder.getOrderButton.visibility = View.GONE
                holder.dropIconButton.setImageResource(R.drawable.baseline_arrow_drop_down_24)
                tablesList.isExpanded = false
            } else {
                holder.cardView.startAnimation(expand)
                holder.getOrderButton.visibility = View.VISIBLE
                holder.dropIconButton.setImageResource(R.drawable.baseline_arrow_drop_up_24)
                tablesList.isExpanded = true
            }
        }
    }


    private fun isAnyItemExpanded(position: Int){
        val temp = tables.indexOfFirst { it.isExpanded}

        if (temp >= 0 && temp != position){
            tables[temp].isExpanded = false
            notifyItemChanged(temp , 0)
        }
    }
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }


    override fun getItemCount(): Int = tables.size
}