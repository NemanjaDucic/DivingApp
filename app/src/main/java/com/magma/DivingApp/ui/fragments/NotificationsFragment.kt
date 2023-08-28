package com.magma.DivingApp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.adapters.NotificationsAdapter
import com.magma.DivingApp.databinding.FragmentNotificationBinding
import com.magma.DivingApp.ui.notifications.ActivityCreateJob
import com.magma.DivingApp.ui.notifications.ActivityJobList

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
        binding.recyclerNotifications.isVisible = false
        binding.imageLogoTwo.isVisible = true
        return binding.root
    }

    private fun init() {
        binding.buttonCheck.setOnClickListener {
            val intent = Intent(context,ActivityCreateJob::class.java)
            startActivity(intent)
        }
        binding.buttonJobs.setOnClickListener {
            val intent = Intent(context,ActivityJobList::class.java)
            startActivity(intent)
        }
    }


}