package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R

class NotificationsAdapter (
    private var notifications: ArrayList<String>
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
        return notifications.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


    }

//    fun setProductList(products: ArrayList<String>) {
//        productsArray = products
//        notifyDataSetChanged()
//    }
}