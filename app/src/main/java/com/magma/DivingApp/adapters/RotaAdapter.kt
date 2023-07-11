package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.model.LogModel
import com.magma.DivingApp.model.RotaModel

class RotaAdapter (
    var items :ArrayList<RotaModel>
) : RecyclerView.Adapter<RotaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rota, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.role.text = items[position].role
        holder.times.text =  "x " + items[position].times

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val name: TextView = itemView.findViewById(R.id.nameTV)
        val role:TextView = itemView.findViewById(R.id.roleTV)
        val times:TextView = itemView.findViewById(R.id.timesTV)

    }
    fun setdData(data: ArrayList<RotaModel>) {
        items = data
        notifyDataSetChanged()
    }

}