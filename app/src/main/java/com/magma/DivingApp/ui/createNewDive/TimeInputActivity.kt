package com.magma.DivingApp.ui.createNewDive

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.magma.DivingApp.databinding.ActivityInputTimeBinding
import java.time.Duration

class TimeInputActivity:AppCompatActivity() {
    private lateinit var binding: ActivityInputTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val editText1 = binding.tf1
        val editText2 = binding.tf2
        binding.calcButton.setOnClickListener {
            val minutes1 = editText1.text.toString().toLong()
            val minutes2 = editText2.text.toString().toLong()

            val difference = minutes2 - minutes1
            val hours = difference / 60
            val remainingMinutes = difference % 60

            println("Difference: $hours hours $remainingMinutes minutes")
        }
    }

}