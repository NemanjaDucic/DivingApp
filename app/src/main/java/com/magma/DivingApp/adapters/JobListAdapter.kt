package com.magma.DivingApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.interfaces.JobClickedInterface
import com.magma.DivingApp.model.JobModel

class JobListAdapter (
    private var jobs: ArrayList<JobModel>,
   private val jobListener:JobClickedInterface
) : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = jobs[position].jobTitle
        holder.text.setOnClickListener {
            jobListener.jobClicked(jobs[position])
        }
        holder.tags.text ="Contact: \n" + jobs[position].link

    }

    override fun getItemCount(): Int {
        return jobs.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val text:TextView = itemView.findViewById(R.id.tvName)
        val tags :TextView = itemView.findViewById(R.id.holderTV)

    }

    fun setProductList(joblist: ArrayList<JobModel>) {
        jobs = joblist
        notifyDataSetChanged()
    }
}