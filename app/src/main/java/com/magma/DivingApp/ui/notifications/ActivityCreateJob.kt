package com.magma.DivingApp.ui.notifications

import android.R
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.magma.DivingApp.databinding.ActivityCreateJobOfferBinding
import com.magma.DivingApp.helpers.FirebaseWizard
import com.magma.DivingApp.model.JobModel
import java.util.*
import kotlin.collections.ArrayList

class ActivityCreateJob:AppCompatActivity() {
    private lateinit var  wizard:FirebaseWizard
    private var category = "off"
    private val ofJobsArray = arrayListOf("Diving Supervisors",
        "Air Divers", "Saturation (Sat) Divers", "Bell Divers", "Mixed Gas Divers", "Closed Bell Divers",
        "Surface Supplied Divers", "Underwater Welder Divers", "Construction Divers", "Inspection Divers",
        "NDT (Non-Destructive Testing) Divers", "Salvage Divers", "Decommissioning Divers", "Video and Photo Documentation Divers",
        "Underwater Welding Inspectors", "Hyperbaric Chamber Operators", "Dive Medic/Paramedic", "Dive Safety Officer",
        "Environmental Diving Specialists", "Research Divers")
    private val rovJobsArray = arrayListOf("ROV Supervisors","ROV Pilots", "ROV Technicians", "ROV Engineers",
            "ROV Maintenance Technicians", "ROV Electricians", "ROV Hydraulic Technicians", "ROV Operations Managers",
            "ROV Project Managers", "ROV Support Technicians", "ROV Camera Operators", "ROV Survey Technicians",
            "ROV Data Analysts", "ROV Equipment Specialists", "ROV Maintenance Engineers", "ROV Tooling Technicians",
            "ROV Launch and Recovery (LAR) Operators", "ROV Navigation Specialists")
    private val offTags = arrayListOf("Underwater Welding", "Construction", "Inspection", "Saturation Diving", "Air Diving",
            "Mixed Gas Diving", "Closed Bell Diving", "Surface Supplied Diving", "Salvage", "Decommissioning", "NDT (Non-Destructive Testing)",
            "Photography and Videography", "Marine Biology Research", "Environmental Monitoring", "Hyperbaric Chamber Operations",
            "Diver Medic/Paramedic", "Dive Safety", "Deep Diving", "Search and Recovery", "Welding Inspection", "Welder Diver",
            "Underwater Cutting", "Marine Archaeology", "Commercial Diving", "Underwater Construction")
    private val rovTags = arrayListOf("Remotely Operated Vehicles", "ROV Pilot", " ROV Technician", "ROV Engineer",
            "ROV Maintenance", "ROV Operations", "ROV Navigation", "ROV Camera Operation", "ROV Surveying", "ROV Data Analysis",
            "ROV Equipment Maintenance", "ROV Tooling", "ROV Launch and Recovery (LAR)", "Subsea Inspection", "Subsea Intervention",
            "Underwater Robotics", "Subsea Cable Inspection", "Underwater Mapping", "ROV Pilot Technician", "ROV Electrical Systems",
            "ROV Hydraulic Systems", "Subsea Construction", "ROV Project Management", "ROV Operations Management",
    )
    private var tagsAdded = ArrayList<String>()
    private lateinit var binding:ActivityCreateJobOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateJobOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        wizard = ViewModelProvider(this)[FirebaseWizard::class.java]
        binding.jobSpinner.adapter  = ArrayAdapter(this, R.layout.simple_list_item_1, ofJobsArray)
        binding.tagSpinner.adapter  = ArrayAdapter(this, android.R.layout.simple_list_item_1, offTags)
        val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

        binding.buttonOff.setOnClickListener {
            binding.jobSpinner.adapter  = ArrayAdapter(this, R.layout.simple_list_item_1, ofJobsArray)
            binding.tagSpinner.adapter  = ArrayAdapter(this, android.R.layout.simple_list_item_1, offTags)
        }
        binding.buttonROV.setOnClickListener {
            binding.jobSpinner.adapter  = ArrayAdapter(this, R.layout.simple_list_item_1, rovJobsArray)
            binding.tagSpinner.adapter  = ArrayAdapter(this, android.R.layout.simple_list_item_1, rovTags)
        }
        binding.tagSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              tagsAdded.add(binding.tagSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.createButton.setOnClickListener {
            if(binding.etDescription.text.toString() == ""){
                Toast.makeText(this , "You must Add Description", Toast.LENGTH_SHORT).show()
            } else if (tagsAdded.isEmpty()){
                Toast.makeText(this , "Please Add Tags To Job", Toast.LENGTH_SHORT).show()

            } else {
                val random = UUID.randomUUID().toString()
                wizard.createJobOffer(
                    JobModel(
                        binding.jobSpinner.selectedItem.toString(),
                        category,
                        tagsAdded,
                        binding.etDescription.text.toString(),
                        binding.etLink.text.toString(),
                        random,
                        sharedPreferences.getString("userID","Unknown") ,
                        false
                    ),random
                ).let {
                 Snackbar.make(binding.root,"Offer Created, Will be Displayed When Approved by Admin",Snackbar.LENGTH_SHORT).show()

                }
            }
        }
    }
}