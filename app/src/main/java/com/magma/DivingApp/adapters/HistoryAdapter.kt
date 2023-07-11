package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.model.DiveModel
import com.magma.DivingApp.ui.home.HistoryActivity

class HistoryAdapter(
    private var divesList: ArrayList<DiveModel>,
    val listener: HistoryActivity
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTV.text = divesList[position].date
        holder.locationTV.text = divesList[position].location
        holder.usersTV.text = divesList[position].diver1 + divesList[position].sByDriver
        holder.reportNumberTV.text = divesList[position].diveReportNumber
       holder.itemView.setOnClickListener {
           listener.diveSelected(divesList[position])
       }
    }

    override fun getItemCount(): Int {
        return divesList.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var reportNumberTV : TextView = itemView.findViewById(R.id.blueTvTop)
        var usersTV : TextView = itemView.findViewById(R.id.users_tv)
        var locationTV : TextView = itemView.findViewById(R.id.text_locations)
            var dateTV : TextView = itemView.findViewById(R.id.dateTV)


    }

    fun setDiveHistory(dives: ArrayList<DiveModel>) {
        divesList = dives
        notifyDataSetChanged()
    }
}