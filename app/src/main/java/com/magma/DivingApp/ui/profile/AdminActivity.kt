package com.magma.DivingApp.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.DivingApp.adapters.AdminAdapter
import com.magma.DivingApp.databinding.AdminLayoutBinding
import com.magma.DivingApp.helpers.FirebaseWizard
import com.magma.DivingApp.interfaces.AdminInterface
import com.magma.DivingApp.model.JobModel

class AdminActivity:AppCompatActivity(), AdminInterface {
    private lateinit var binding:AdminLayoutBinding
    private lateinit var adapter:AdminAdapter
    private lateinit var wizard:FirebaseWizard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        adapter = AdminAdapter(arrayListOf(),this)
        wizard = ViewModelProvider(this)[FirebaseWizard::class.java]
        binding.adminRV.adapter = adapter
        binding.adminRV.layoutManager = LinearLayoutManager(this)
        wizard.getJobsForAdmin()
        wizard.jobsAdminList.observe(this){
            adapter.setProductList(it)
        }


    }

    override fun decline(job: JobModel) {
        wizard.removeJob(job.uid!!).let {
            Toast.makeText(this, "Job Post Removed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun allow(job: JobModel) {
        wizard.setTrue(job.uid!!).let {
            Toast.makeText(this, "Job Post Allowed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun show(job: JobModel) {
        showSimpleAlert(job.jobTitle!!,job.description!!,job.link!!,job.owner!!,job.tags!!)
    }

    private fun showSimpleAlert(titleJob:String,messageJob:String,link:String,owner:String,tagsmsg:ArrayList<String>) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dialogView: View = inflater.inflate(com.magma.DivingApp.R.layout.admin_dialog_layout,null)

        val message = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.messageTV)
        val title = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.titleTV)
        val made = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.madeByTV)
        val tags = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.tagsTV)
        val contact = dialogView.findViewById<TextView>(com.magma.DivingApp.R.id.contactTV)


        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$link")
            putExtra(Intent.EXTRA_SUBJECT, "$title job application")
        }
        title.text = "Job Title: " + titleJob
        message.text ="Desctiption: " + messageJob
        contact.text = "Contact: " + link
        made.text = "Made by: " + owner
        tags.text = "Job Tags: " + tagsmsg.toString()




    }
}