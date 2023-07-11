package com.magma.DivingApp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.DivingApp.databinding.FragmentProfileBinding
import com.magma.DivingApp.model.UserModel

class ProfileFragment:Fragment(){
    private lateinit var binding: FragmentProfileBinding
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
    private lateinit var attachedContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachedContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.etEmail.isEnabled = false
        binding.etName.isFocusable = false
        binding.etId.isEnabled = false
        binding.etName.setOnClickListener {
            showAlertDialogWithTwoButtons()
        }
        val sharedPreferences = attachedContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        databaseInstance.child(sharedPreferences?.getString("userID","")!!).child("data").get().addOnCompleteListener {

            val user =   it.result.getValue(UserModel::class.java)
            binding.etEmail.setText(user?.email)
            binding.etId.setText(user?.uid)
            binding.etName.setText(user?.name)
            binding.topText.text = user?.name
            val n = user?.name
            binding.botText.text = "@$n"


        }

    }
    private fun showAlertDialogWithTwoButtons() {
        val sharedPreferences = attachedContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val inputEditTextField = EditText(attachedContext)
        val dialog = AlertDialog.Builder(attachedContext)
            .setTitle("Enter Your Name")
            .setMessage("this will be saved as your username")
            .setView(inputEditTextField)
            .setPositiveButton("OK") { _, _ ->
                val editTextInput = inputEditTextField .text.toString()

                val paddingValue = resources.getDimensionPixelSize(com.mapbox.search.ui.R.dimen.material_filled_edittext_font_1_3_padding_bottom) // You can define your desired padding value in dimensions.xml
                inputEditTextField.setPadding(paddingValue, paddingValue, paddingValue, paddingValue)

                databaseInstance.child(sharedPreferences?.getString("userID","")!!).child("data").child("name").setValue(editTextInput).let {
                    activity?.recreate()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

//    private fun listeners(){
//        backButtonListener()
//        createTournamentListener()
//        createGroupListener()
//    }

//    private fun backButtonListener(){
//        binding.imgBackButtonAddFragment.setOnClickListener{
//            activity?.finish()
//        }
//    }
//
//    private fun createTournamentListener(){
//        binding.btnCreateTournament.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putBoolean("tournament",true)
//            Utils.intent(binding.root.context,CreateActivity::class.java,bundle)
//        }
//    }
//
//    private fun createGroupListener(){
//        binding.btnCreateGroup.setOnClickListener{
//            Utils.intent(binding.root.context,CreateGroupActivity::class.java,null)
//        }
//    }
}