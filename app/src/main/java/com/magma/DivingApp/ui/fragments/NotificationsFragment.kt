package com.magma.DivingApp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.adapters.NotificationsAdapter
import com.magma.DivingApp.databinding.FragmentNotificationBinding

class NotificationsFragment:Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private lateinit var attachedContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachedContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        init()
        recyclerView = binding.recyclerNotifications
        adapter = NotificationsAdapter(arrayListOf("","","","","",""))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(attachedContext)
        return binding.root
    }

    private fun init() {
//        listeners()
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