package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.interfaces.AdminInterface
import com.magma.DivingApp.interfaces.JobClickedInterface
import com.magma.DivingApp.model.JobModel

class AdminAdapter (
    private var jobs: ArrayList<JobModel>,
    private val jobListener: AdminInterface
) : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = jobs[position].jobTitle
        holder.text2.text = jobs[position].owner
        holder.imagePlus.setOnClickListener {
            jobListener.allow(jobs[position])

        }
        holder.itemView.rootView.setOnClickListener {
            jobListener.show(jobs[position])
        }
        holder.imageMinus.setOnClickListener {
            jobListener.decline(jobs[position])
        }

    }

    override fun getItemCount(): Int {
        return jobs.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val text: TextView = itemView.findViewById(R.id.tvjob)
        val text2: TextView = itemView.findViewById(R.id.tvjobowner)
        val imagePlus:ImageView = itemView.findViewById(R.id.plusImage)
        val imageMinus:ImageView = itemView.findViewById(R.id.minusImage)
    }

    fun setProductList(joblist: ArrayList<JobModel>) {
        jobs = joblist
        notifyDataSetChanged()
    }
}