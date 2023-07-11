package com.magma.DivingApp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.DivingApp.databinding.ActivityRegisterBinding
import com.magma.DivingApp.model.UserModel
import com.magma.DivingApp.ui.home.HomeActivity

class RegisterActivity:AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private var flag :Boolean ?= false
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.etPasswordConfirm.isVisible = false
        binding.tvReg.setOnClickListener {
            if (flag == false) {
                binding.tvReg.text = "Already Have an Account? Log in"
                binding.etPasswordConfirm.isVisible = true
                binding.tvTop.text = "Register"
                binding.button.text = "Register"

                flag = true
            } else {
                binding.tvReg.text = "Don't have an Account? Register"
                binding.etPasswordConfirm.isVisible = false
                binding.tvTop.text = "Log in"
                flag = false
                binding.button.text = "Log in"
            }
        }
        binding.button.setOnClickListener {
            if (flag == false){
                tryLogin()
            } else {
                tryRegister()
            }
        }
    }
    private fun tryLogin(){
        var email = binding.etMail.text.toString()
        var password = binding.etPassword.text.toString()
        if (email != "" && email != null && password != "" && password != null){


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { singtask ->
                if (singtask.isSuccessful){
                    val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

                    val intent = Intent(this@RegisterActivity,HomeActivity::class.java)
                    sharedPreferences.edit().putString("userID",singtask.result.user?.uid).apply()
                    sharedPreferences.edit().putInt("adReward",1).apply()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun tryRegister() {

        var email = binding.etMail.text.toString()
        var password = binding.etPassword.text.toString()
        var cpass = binding.etPasswordConfirm.text.toString()
        if (email != "" && email != null && password != "" && password != null){
            if (password == cpass) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
               val uid = task.result.user!!.uid
                val user = UserModel(email = email, uid = uid)
               databaseInstance.child("users").child(uid).child("data").setValue(user).addOnCompleteListener {
                   auth.signInWithEmailAndPassword(email, password)
                       .addOnCompleteListener { singtask ->
                           if (singtask.isSuccessful){
                              val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

                               val intent = Intent(this@RegisterActivity,HomeActivity::class.java)
                               sharedPreferences.edit().putString("userID",uid).apply()
                               sharedPreferences.edit().putInt("adReward",1).apply()

                               startActivity(intent)
                           } else {
                               Toast.makeText(this, "Please Try Again Later", Toast.LENGTH_SHORT).show()

                           }
                       }

               }

            } else {
                Toast.makeText(this, "Please Try Again Later", Toast.LENGTH_SHORT).show()
            }
        }
            } else {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()

            }
    } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()

        }
    }
}