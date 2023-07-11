package com.magma.DivingApp.ui.createNewDive

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.magma.DivingApp.helpers.Utilities
import com.magma.DivingApp.databinding.ActivityDivingDetailsBinding
import com.magma.DivingApp.model.DiveModel

class NewDiveDivingMethodActivity:AppCompatActivity() {
    private lateinit var binding:ActivityDivingDetailsBinding
    var method = ""
    private var dive = DiveModel()
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dive = intent.getSerializableExtra("dive") as DiveModel

        listeners()
        disableboxes()
    }
    private fun listeners()

    {
        println(dive.diveType)
        if (dive.diveType == "Scuba"){
            binding.checkbox1.isChecked = true

        } else if (dive.diveType == "Surf.Supp") {
            binding.checkbox2.isChecked = true

        }else if (dive.diveType == "Wet Bell") {
            binding.checkbox3.isChecked = true

        }else if (dive.diveType == "S.R.P") {
            binding.checkbox4.isChecked = true

        }else if (dive.diveType == "Bell Bounce") {
            binding.checkbox5.isChecked = true

        }else if (dive.diveType == "T.U.P") {
            binding.checkbox6.isChecked = true

        }
        binding.button.setOnClickListener {

                if (checkAir()){
                    if (check()){
                        dive.bottomMixAnalysis = binding.bottomixET.text.toString()
                        dive.superintendent = binding.personelET.text.toString()
                        dive.diver1 = binding.diver1ET.text.toString()
                        dive.diver2 = binding.diver2ET.text.toString()
                        dive.winchOP = binding.opET.text.toString()
                        dive.chamberOP = binding.chamberET.text.toString()
                        dive.sByDriver = binding.sdiverET.text.toString()
                        dive.divingMethod = method
                        if (binding.airCheckbox.isChecked) {
                            dive.bottomMix = "Air"
                        } else {
                            dive.bottomMix = "Nitorx-Mixed Gas"

                        }
                        bundle.putSerializable("dive",dive)
                        Utilities.intent(this,NewDiveEquipmentCheck::class.java,bundle)

                    } else {
                        Toast.makeText(this,"Please check  fill in all the fields", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this,"Please check  air box", Toast.LENGTH_SHORT).show()

                }



        }
        binding.checkbox1.setOnClickListener {
            method = binding.checkbox1.text.toString()
            binding.checkbox1.isChecked = true
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }
        binding.checkbox2.setOnClickListener {
            method = binding.checkbox2.text.toString()

            binding.checkbox2.isChecked = true
            binding.checkbox1.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }
        binding.checkbox3.setOnClickListener {
            method = binding.checkbox3.text.toString()

            binding.checkbox3.isChecked = true
            binding.checkbox2.isChecked = false
            binding.checkbox1.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }
        binding.checkbox4.setOnClickListener {
            method = binding.checkbox4.text.toString()

            binding.checkbox4.isChecked = true
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox1.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }
        binding.checkbox5.setOnClickListener {
            method = binding.checkbox5.text.toString()

            binding.checkbox5.isChecked = true
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox1.isChecked = false
            binding.checkbox6.isChecked = false
        }
        binding.checkbox6.setOnClickListener {
            method = binding.checkbox6.text.toString()

            binding.checkbox6.isChecked = true
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox1.isChecked = false
        }
        binding.airCheckbox.setOnClickListener {
            binding.airCheckbox.isChecked = true
            binding.nitroCheckbox.isChecked = false
        }
        binding.nitroCheckbox.setOnClickListener {
            binding.airCheckbox.isChecked = false
            binding.nitroCheckbox.isChecked = true
        }

    }


    private fun checkAir ():Boolean {
        if (binding.airCheckbox.isChecked || binding.nitroCheckbox.isChecked){
            return true
        } else {
            return false
        }
    }
    private fun check () :Boolean{
        val editTextList = listOf(
            binding.bottomixET,
            binding.personelET,
            binding.diver1ET,
            binding.diver2ET,
            binding.sdiverET,
            binding.opET,
            binding.chamberET

        )


        for (editText in editTextList) {
            when {
                editText.text.toString().isEmpty() -> return false

            }
        }

        return true
    }
    private fun disableboxes(){
        binding.checkbox1.isEnabled = false
        binding.checkbox2.isEnabled = false
        binding.checkbox3.isEnabled = false
        binding.checkbox4.isEnabled = false
        binding.checkbox5.isEnabled = false
        binding.checkbox6.isEnabled = false

    }
}