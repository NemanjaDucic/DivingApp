package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.model.LogModel

class BlueLogAdapter (
    var items :ArrayList<LogModel>
) : RecyclerView.Adapter<BlueLogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.imte_log_blue, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = items[position].time
        holder.desc.text     = items[position].activity


    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val time: TextView = itemView.findViewById(R.id.timeTV)
        val desc:TextView = itemView.findViewById(R.id.texter)

    }
    fun setdData(data: ArrayList<LogModel>) {
        items = data
        notifyDataSetChanged()
    }

}