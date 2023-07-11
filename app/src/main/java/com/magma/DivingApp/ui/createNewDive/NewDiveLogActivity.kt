package com.magma.DivingApp.ui.createNewDive

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.DivingApp.adapters.LogAdapter
import com.magma.DivingApp.databinding.ActivityLogBinding
import com.magma.DivingApp.interfaces.ActivitySelectedListener
import com.magma.DivingApp.model.LogModel
import com.magma.DivingApp.ui.home.HomeActivity
import java.time.LocalTime

class NewDiveLogActivity:AppCompatActivity(),ActivitySelectedListener {
    private lateinit var binding:ActivityLogBinding
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var countUpTimer: CountDownTimer? = null
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var isCountingUp = true
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:LogAdapter
    private var random = ""
    private var logArray = arrayListOf<LogModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        val sharedPreference =  getSharedPreferences("pref", Context.MODE_PRIVATE)
        val id = sharedPreference.getString("userID","")

        adapter = LogAdapter(arrayListOf(),this)
        recyclerView = binding.logRV
//        random = intent.getStringExtra("random")!!
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.button.setOnClickListener {
            val currentTime =
                LocalTime.now()
            val currentHour = currentTime.hour
            val currentMinute = currentTime.minute
            logArray.add(LogModel("$currentHour $currentMinute", "ff"))
            adapter.setData(logArray)

        }
          binding.stopButton.setOnClickListener {
              if (isTimerRunning) {
                  stopTimer()
              }
          }
//
           binding.reverseButton.setOnClickListener {
                if (!isTimerRunning) {
                    startCountDownTimer()
                }

            }
            binding.startButtonClick.setOnClickListener {
                if (!isTimerRunning) {
                    startCountUpTimer()
                }
            }

        binding.finishButton.setOnClickListener {
            databaseInstance.child("users").child(id!!).child("diveLogs").child(random).setValue(logArray).addOnCompleteListener {
                val intent = Intent(this@NewDiveLogActivity,HomeActivity::class.java)
                startActivity(intent)
            }
        }
        binding.backbutton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onItemSelected(value: String,pos:Int) {
        logArray[pos].activity = value
        adapter.setData(logArray)

    }
    private fun startCountUpTimer() {
        countUpTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            var count = 0L

            override fun onTick(millisUntilFinished: Long) {
                count++
                binding.descriptionTV.text = count.toString()
                println(count)
            }

            override fun onFinish() {
                // Not used for count up timer
            }
        }

        countUpTimer?.start()
        isTimerRunning = true
    }

    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            var count= binding.descriptionTV.text.toString().toLong()

            override fun onTick(millisUntilFinished: Long) {
                count--
                binding.descriptionTV.text = count.toString()
                if (count.toInt() == 0){
                   stopTimer()
                }
            }

            override fun onFinish() {
                // Not used for count down timer
            }
        }

        countDownTimer?.start()
        isTimerRunning = true
        isCountingUp = false
    }

    private fun stopTimer() {
        if (isCountingUp) {
            countUpTimer?.cancel()
        } else {
            countDownTimer?.cancel()
        }
        isTimerRunning = false
        isCountingUp = true
    }
}