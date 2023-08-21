package com.magma.DivingApp.interfaces

import com.magma.DivingApp.model.JobModel

interface AdminInterface {
    fun decline(job:JobModel)
    fun allow(job: JobModel)
    fun show(job:JobModel)
}