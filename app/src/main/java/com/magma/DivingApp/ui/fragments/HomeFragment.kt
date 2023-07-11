package com.magma.DivingApp.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.magma.DivingApp.databinding.FragmentHomeBinding
import com.magma.DivingApp.databinding.FragmentProfileBinding
import com.magma.DivingApp.ui.createNewDive.NewDiveActivity
import com.magma.DivingApp.ui.home.HistoryActivity

class HomeFragment:Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var attachedContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachedContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        init()
        MobileAds.initialize(attachedContext) {}
        return binding.root
    }


    private fun init() {
        binding.blueHolder.setOnClickListener {
            val intent = Intent(attachedContext,NewDiveActivity::class.java)
            startActivity(intent)
        }
        binding.lightGrayHolder.setOnClickListener {
            val intent = Intent(attachedContext,HistoryActivity::class.java)
            startActivity(intent)
        }
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