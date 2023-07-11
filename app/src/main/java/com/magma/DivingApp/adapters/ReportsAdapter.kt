package com.magma.DivingApp.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.interfaces.TableClickedInterface

class ReportsAdapter (
    private var reports: ArrayList<String>,
    private val listener:TableClickedInterface
) : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.topText.text = reports[position]
        holder.leftImage.setOnClickListener {
            listener.dlClicked(position.toString())
        }
    holder.rightImage.setOnClickListener {
        listener.viewClicked(position.toString())
    }
    }

    override fun getItemCount(): Int {
        return reports.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    val topText:TextView = itemView.findViewById(R.id.tvTop)
        val leftImage : ImageView = itemView.findViewById(R.id.imgLeft)
        val rightImage :ImageView = itemView.findViewById(R.id.imgRight)

    }

    fun setList(products: ArrayList<String>) {
        reports = products
        notifyDataSetChanged()
    }
}