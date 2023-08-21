package com.magma.DivingApp.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.magma.DivingApp.model.JobModel
import java.util.Random
import java.util.UUID

class FirebaseWizard:ViewModel() {
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var _jobsListLiveData = MutableLiveData<ArrayList<JobModel>>()
    var jobsList = _jobsListLiveData as LiveData <ArrayList<JobModel>>
    private var _adminJobsListLiveData = MutableLiveData<ArrayList<JobModel>>()
    var jobsAdminList = _adminJobsListLiveData as LiveData <ArrayList<JobModel>>

    fun createJobOffer(job:JobModel,random:String){


        databaseInstance.child("jobPosts").child(random).setValue(
            job
        )
    }
    fun getJobs(){
        val jobPostsRef = databaseInstance.child("jobPosts")
        val query: Query = jobPostsRef.orderByChild("status").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val jobModels: MutableList<JobModel> = mutableListOf()

                for (postSnapshot in snapshot.children) {
                    val jobModel = postSnapshot.getValue(JobModel::class.java)
                    jobModel?.let {
                        jobModels.add(it)
                    }
                }

                _jobsListLiveData.postValue(jobModels as ArrayList<JobModel>?)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun getJobsForAdmin(){
        val jobPostsRef = databaseInstance.child("jobPosts")
        val query: Query = jobPostsRef.orderByChild("status").equalTo(false)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val jobModels: MutableList<JobModel> = mutableListOf()

                for (postSnapshot in snapshot.children) {
                    val jobModel = postSnapshot.getValue(JobModel::class.java)
                    jobModel?.let {
                        jobModels.add(it)
                    }
                }

                _adminJobsListLiveData.postValue(jobModels as ArrayList<JobModel>?)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
 fun setTrue(uid:String){
     databaseInstance.child("jobPosts").child(uid).child("status").setValue(true)
 }
    fun removeJob(uid:String){
        databaseInstance.child("jobPosts").child(uid).removeValue()

    }
}