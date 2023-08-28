package com.magma.DivingApp.ui.createNewDive

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.magma.DivingApp.helpers.Utilities
import com.magma.DivingApp.databinding.ActivityNewDiveEqipmentCheckBinding
import com.magma.DivingApp.model.DiveModel

class NewDiveEquipmentCheck:AppCompatActivity() {
    private lateinit var binding :ActivityNewDiveEqipmentCheckBinding
    private var dive = DiveModel()
    private var accident = ""
    private val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDiveEqipmentCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dive = intent.getSerializableExtra("dive") as DiveModel
        listeners()
    }
    private fun listeners () {
        binding.checkobxNo.setOnClickListener {
            accident = "no"
            binding.checkobxNo.isChecked = true
            binding.yesCechkbox.isChecked = false
        }
        binding.yesCechkbox.setOnClickListener {
            accident = "yes"
            binding.checkobxNo.isChecked = false
            binding.yesCechkbox.isChecked = true
        }
        binding.button.setOnClickListener {
            if (fullCheck()){
                dive.diver1HeadGear = binding.headgearTV.text.toString()
                dive.diver1Suit = binding.suitET.text.toString()
                dive.diver1BallOut = binding.balloutET.text.toString()
                dive.sByHeadGear = binding.headgear2TV.text.toString()
                dive.sByBallOut = binding.ballout2ET.text.toString()
                dive.sBySuit = binding.suit2ET.text.toString()
                dive.accident = binding.acnoET.text.toString()
                bundle.putSerializable("dive",dive)
                Utilities.intent(this,NewDiveReadyToDiveActivity::class.java,bundle)

            } else {
                Toast.makeText(this,"Please Fill in all Info",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun check () :Boolean{
        val editTextList = listOf(
            binding.headgerET,


        )


        for (editText in editTextList) {
            when {
                editText.text.toString().isEmpty() -> return false

            }
        }

        return true
    }
    private fun fullCheck() :Boolean {
        if (check() && accident != ""){
            return true
        } else {
            return  false
        }
    }
}