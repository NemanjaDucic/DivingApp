package com.magma.DivingApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.magma.DivingApp.R
import com.magma.DivingApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init () {
        binding.getStartedButton.setOnClickListener {
            val intent  = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}