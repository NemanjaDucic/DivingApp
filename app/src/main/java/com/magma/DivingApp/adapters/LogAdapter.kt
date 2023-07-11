package com.magma.DivingApp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.interfaces.ActivitySelectedListener
import com.magma.DivingApp.model.LogModel

class LogAdapter (
    var items :ArrayList<LogModel>,
    private val listener :ActivitySelectedListener

) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private var somenumber:Int = 0


    private val  predefinedTasks = arrayListOf("Pick Activity","1.Visual inspection of platform structures and components",
        "2.Cathodic protection survey to assess the effectiveness of corrosion protection systems",
        "3.Ultrasonic thickness survey to measure the thickness of steel structures and identify potential areas of corrosion or degradation",
        "4.NDT (Non-Destructive Testing) inspections, such as magnetic particle testing or dye penetrant testing, to detect surface defects or cracks",
        "5.High-pressure water jetting or cleaning to remove marine growth or debris from platform surfaces",
        "6.Installation or replacement of anodes for corrosion protection",
        "7.Inspection and maintenance of platform risers, pipelines, and underwater equipment",
        "8.Installation or repair of underwater sensors or monitoring devices",
        "9.Assistance in underwater construction or repair activities, such as installation of clamps, brackets, or grout bags",
        "10.Underwater inspection of the SBM structure",
        "11.Cleaning and removing marine growth from the SBM",
        "12.Installation or replacement of anodes for cathodic protection",
        "13.Inspection and repair of mooring lines and connections",
        "14.Welding or cutting operations for structural repairs",
        "15.Underwater cleaning and maintenance of SBM hoses and connections",
        "16.Installation, removal, or repair of riser clamps and brackets",
        "17.Underwater welding or cutting to repair damaged sections of the pipeline",
        "18.Installation or replacement of pipeline connectors, flanges, or valves",
        "19.Bolting or clamping of pipeline components for temporary or permanent repairs",
        "20.Installation or repair of pipeline supports, brackets, or clamps",
        "21.Application of protective coatings or corrosion inhibitors on repaired sections",
        "22.Testing and verification of repaired pipeline sections for integrity and leak-free operation",
        "23.Conducting ultrasonic thickness measurements to assess the condition of the pipeline and identify potential areas of corrosion or degradation",
        "24.Visual inspection of the pipeline for signs of damage, leaks, or structural issues",
        "25.Identification and mapping of defects or anomalies using underwater inspection techniques",
        "26.Performing magnetic particle testing or dye penetrant testing to detect surface cracks or defects"
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") pos: Int) {

        holder.picker.adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_list_item_1, predefinedTasks)
        holder.time.text = items[pos].time

        holder.picker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
             listener.onItemSelected(predefinedTasks[position],pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



    }

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var t:Int = 0
        val time:TextView = itemView.findViewById(R.id.timeTV)
        val picker :Spinner = itemView.findViewById(R.id.spinner)
        init {
            picker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }


            }
        }

}
    fun setData (data:ArrayList<LogModel>){
        items = data
        notifyItemInserted(items.count())
    }

}