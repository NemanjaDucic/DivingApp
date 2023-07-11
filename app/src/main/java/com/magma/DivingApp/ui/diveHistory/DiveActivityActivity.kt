package com.magma.DivingApp.ui.diveHistory


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.DivingApp.R
import com.magma.DivingApp.adapters.RotaAdapter
import com.magma.DivingApp.databinding.ActivityactivityBinding
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*


class DiveActivityActivity: AppCompatActivity() {
    private lateinit var binding:ActivityactivityBinding
    private lateinit var adapter:RotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }
    private fun init(){
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)
        val horizontalCalendar = HorizontalCalendar.Builder(
            this,
            com.magma.DivingApp.R.id.calendarView
        )
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .build()
        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {

                println(date)
            }
        }
        adapter = RotaAdapter(arrayListOf())
        binding.rotaRV.adapter = adapter
        binding.rotaRV.layoutManager = LinearLayoutManager(this)
    }
}