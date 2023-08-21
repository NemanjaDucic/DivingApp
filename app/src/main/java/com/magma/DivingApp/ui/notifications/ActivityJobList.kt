package com.magma.DivingApp.ui.notifications

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.DivingApp.adapters.JobListAdapter
import com.magma.DivingApp.databinding.FragmentNotificationBinding
import com.magma.DivingApp.helpers.FirebaseWizard
import com.magma.DivingApp.interfaces.JobClickedInterface
import com.magma.DivingApp.model.JobModel


class ActivityJobList:AppCompatActivity(), JobClickedInterface {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var wizard: FirebaseWizard
    private lateinit var adapter:JobListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

    }
    private fun init(){
        wizard = ViewModelProvider(this)[FirebaseWizard::class.java]
        wizard.getJobs()
        binding.titleTVE.text = "Available Jobs"
        binding.buttonJobs.isVisible = false
        binding.buttonCheck.isVisible = false
        adapter = JobListAdapter(arrayListOf(),this)
        binding.recyclerNotifications.adapter = adapter
        binding.recyclerNotifications.layoutManager = LinearLayoutManager(this)
        wizard.jobsList.observe(this){
            adapter.setProductList(it)
        }
    }

    override fun jobClicked(job: JobModel) {

      showSimpleAlert(job.jobTitle!!,job.description!!,job.link!!)
    }
    private fun showSimpleAlert(titleJob:String,messageJob:String,link:String) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dialogView: View = inflater.inflate(com.magma.DivingApp.R.layout.dialog_custom_layout,null)

        val button = dialogView.findViewById<Button>(com.magma.DivingApp.R.id.btDismissCustomDialog)
        val message = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.messageTV)
        val title = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.titleTV)

        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$link")
             putExtra(Intent.EXTRA_SUBJECT, "$title job application")
        }
        title.text = titleJob
        message.text = messageJob

            button.setOnClickListener {
                customDialog.dismiss()
                startActivity(intent)
            }

    }
}