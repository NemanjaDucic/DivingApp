package com.magma.DivingApp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.interfaces.HorizontalClickInterface

class HorizontalAdapter (
    private var reports: ArrayList<String>,
    private val listner:HorizontalClickInterface
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.filter.text = reports[position]
        holder.itemView.setOnClickListener {
            listner.itemSelected(reports[position],position)
            selectedItemPosition = position
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position){
            holder.itemView.setBackgroundResource(R.drawable.dark_purple_bcg)
            holder.filter.setTextColor(Color.parseColor("#FFFFFFFF"))
        } else {
            holder.itemView.setBackgroundResource(R.drawable.light_purple_bcg)
            holder.filter.setTextColor(Color.parseColor("#FF000000"))

        }

    }

    override fun getItemCount(): Int {
        return reports.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var filter = itemView.findViewById<TextView>(R.id.filterText)


    }

    fun setProductList(products: ArrayList<String>) {
        reports = products
        notifyDataSetChanged()
    }
}