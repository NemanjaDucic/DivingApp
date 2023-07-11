package com.magma.DivingApp.ui.createNewDive

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.DivingApp.databinding.ActivityReadyToDiveBinding
import com.magma.DivingApp.model.DiveModel
import java.util.*

class NewDiveReadyToDiveActivity:AppCompatActivity() {
    private lateinit var binding:ActivityReadyToDiveBinding
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val halfPeriod: Int = 15
    private val airBreak: Int = 5
    private val onSurfaceBleed: Int = 10
    private var dive = DiveModel()
    private var dt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadyToDiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners(){
        dive = intent.getSerializableExtra("dive") as DiveModel
        val sharedPreference =  getSharedPreferences("pref", Context.MODE_PRIVATE)
        val id = sharedPreference.getString("userID","")
        val random = java.util.UUID.randomUUID().toString()
        binding.lBottomET.isFocusable = false
        binding.lSurficeET.isFocusable = false
        binding.rBottomET.isFocusable = false

        setupTimePicker(binding.lBottomET)
        setupTimePicker(binding.lSurficeET)
        setupTimePicker(binding.rBottomET)


        binding.button.setOnClickListener {
            dive.uid = random
            databaseInstance.child("users").child(id!!).child("dives").child(random).setValue(dive).addOnCompleteListener {
                val intent = Intent(this,NewDiveLogActivity::class.java)
                intent.putExtra("random",random)
                startActivity(intent)
            }
        }
        binding.button.setOnClickListener {
            calculateButtonTapped()
        }
    }
    private fun calculateButtonTapped() {
        val leftSurfaceText = binding.lSurficeET.text.toString()
        val arrivedBottomText = binding.rBottomET.text.toString()
        val leftBottomText = binding.lBottomET.text.toString()

        val leftSurfaceMinutes = convertToMinutes(leftSurfaceText)
        val arrivedBottomMinutes = convertToMinutes(arrivedBottomText)
        val leftBottomMinutes = convertToMinutes(leftBottomText)

        if (leftSurfaceMinutes == null || arrivedBottomMinutes == null || leftBottomMinutes == null) {
            Log.e("DiveProfileVC", "Invalid input")
            return
        }

        val bottomTime = leftBottomMinutes - arrivedBottomMinutes
        val diveTime = calculateDiveTime(
            bottomTime,
            40,
            0,
            0,
            halfPeriod,
            airBreak,
            onSurfaceBleed
        )

        binding.diveTimeLabel.text = "Dive Time: $diveTime minutes"
        dt = diveTime
    }
    private fun calculateDiveTime(
        bottomTime: Int,
        arrivedStop40Minutes: Int,
        arrivedSurface40Minutes: Int,
        leftChamberSurfaceMinutes: Int,
        halfPeriod: Int,
        airBreak: Int,
        onSurfaceBleed: Int
    ): Int {
        val totalBottomTime = bottomTime + arrivedStop40Minutes + arrivedSurface40Minutes + leftChamberSurfaceMinutes
        val totalHalfPeriods = (totalBottomTime - onSurfaceBleed) / (halfPeriod + airBreak)
        val diveTime = totalBottomTime + (totalHalfPeriods * halfPeriod) + onSurfaceBleed

        return diveTime
    }
    private fun convertToMinutes(timeString: String): Int? {
        val timeComponents = timeString.split(":")

        if (timeComponents.size != 2) {
            return null
        }

        val hours = timeComponents[0].toIntOrNull()
        val minutes = timeComponents[1].toIntOrNull()

        return if (hours != null && minutes != null) {
            (hours * 60) + minutes
        } else {
            null
        }
    }

        private fun setupTimePicker(textField: EditText) {
            val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                textField.setText(selectedTime)
            }, 0, 0, true)

            textField.setOnClickListener {
                timePicker.show()
            }
        }
    private fun viewTapped() {
        currentFocus?.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}