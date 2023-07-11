package com.magma.DivingApp.ui.diveHistory

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.magma.DivingApp.adapters.BlueLogAdapter
import com.magma.DivingApp.adapters.HorizontalAdapter
import com.magma.DivingApp.databinding.ActivityHistoryDetailBinding
import com.magma.DivingApp.interfaces.HorizontalClickInterface
import com.magma.DivingApp.model.DiveModel
import com.magma.DivingApp.model.LogModel

class DiveActivityDetails:AppCompatActivity(),HorizontalClickInterface {
    var selected = ""
    val gson = Gson()
    val databaseInstance: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("users")
    private lateinit var binding: ActivityHistoryDetailBinding
    lateinit var adapterBlue: BlueLogAdapter
    private val array = arrayListOf("Details", "Method", "Equipment", "Activity", "Results")
    private lateinit var adapter: HorizontalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selected = intent.getStringExtra("selectedDive")!!
        init()
    }

    private fun init() {
        getDive()
        adapter = HorizontalAdapter(array, this)
        adapterBlue = BlueLogAdapter(arrayListOf())
        binding.blueLogRV.adapter = adapterBlue
        binding.blueLogRV.layoutManager = LinearLayoutManager(this)
        binding.horizontalRV.adapter = adapter
        binding.horizontalRV.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.holderMethod.isVisible = false
        binding.holderEquipment.isVisible = false
        binding.holderResults.isVisible = false
        binding.holderActivity.isVisible = false
        binding.backbutton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getDive() {
        val sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var id = sharedPreferences?.getString("userID", "")!!
        databaseInstance.child(id).child("dives").child(selected).get().addOnCompleteListener {
            val d = it.result
            val jsonKey = gson.toJson(d.key)
            val json = gson.toJson(d.value)
            val data = Gson().fromJson(json, DiveModel::class.java)

            binding.locationTV.text = data.location
            binding.repnumTV.text = data.diveReportNumber
            binding.vesselTV.text = data.vessel
            binding.wtempTV.text = data.waterTemperature
            binding.swellTV.text = data.swell
            binding.clientTV.text = data.client
            binding.dateTV.text = data.date
            binding.currentTVS.text = data.current
            binding.visibilityTVS.text = data.visibility
            binding.cTV.text = data.waterTemperature
            binding.cbsupsurf.text = data.bottomMix
            binding.cbAir.text = data.divingMethod
            binding.bmixanalysistv.text = data.bottomMixAnalysis
            binding.suprvisorTVS.text = data.supervisor
            binding.SuperintendentTV.text = data.superintendent
            binding.diver1TV.text = data.diver1
            binding.sbydiverTVS.text = data.sByDriver
            binding.chamberOpTV.text = data.chamberOP
            binding.wOPTV.text = data.winchOP
            binding.Suit1TV.text = data.diver1Suit
            binding.bout1TV.text = data.diver1BallOut
            binding.hg1TV.text = data.diver1HeadGear
            binding.Suit2TV.text = data.sBySuit
            binding.bout2TV.text = data.sByBallOut
            binding.hg2TV.text = data.sByHeadGear
            binding.acicrepnum.text = data.accidentReportNumber
            binding.cbsmth.text = data.accident
        }
        getLogs()
    }

    private fun getLogs() {
        val sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var id = sharedPreferences?.getString("userID", "")!!
        databaseInstance.child(id).child("diveLogs").child(selected).get().addOnCompleteListener {
            var logarray = arrayListOf<LogModel>()
            logarray.clear()
            for (i in it.result.children){
                val jsonKey = gson.toJson(i.key)
                val json = gson.toJson(i.value)
                val data = Gson().fromJson(json, LogModel::class.java)
                logarray.add(data)
            }
            adapterBlue.setdData(logarray)
        }

    }

    override fun itemSelected(item: String,position:Int) {
        when (item){
            "Details" -> {
                binding.holderDetails.isVisible = true
                binding.holderMethod.isVisible = false
                binding.holderEquipment.isVisible = false
                binding.holderResults.isVisible = false
                binding.holderActivity.isVisible = false
            }
            "Method" -> {
                binding.holderDetails.isVisible = false
                binding.holderMethod.isVisible = true
                binding.holderEquipment.isVisible = false
                binding.holderResults.isVisible = false
                binding.holderActivity.isVisible = false
            }
            "Equipment" -> {
                binding.holderDetails.isVisible = false
                binding.holderMethod.isVisible = false
                binding.holderEquipment.isVisible = true
                binding.holderResults.isVisible = false
                binding.holderActivity.isVisible = false
            }
            "Activity" -> {
                binding.holderDetails.isVisible = false
                binding.holderMethod.isVisible = false
                binding.holderEquipment.isVisible = false
                binding.holderResults.isVisible = false
                binding.holderActivity.isVisible = true
            }
            "Results" -> {
                binding.holderDetails.isVisible = false
                binding.holderMethod.isVisible = false
                binding.holderEquipment.isVisible = false
                binding.holderResults.isVisible = true
                binding.holderActivity.isVisible = false
            }
        }
        println(position)
    }
}