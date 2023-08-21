package com.magma.DivingApp.model

data class JobModel(
    var jobTitle:String ?= "",
    var category: String ?= "",
    var tags :ArrayList<String> ?= arrayListOf(),
    var description:String ?= "",
    var link:String ?= "",
    var uid:String ?= "",
    var owner:String ?= "",
    var status:Boolean ?= false
):java.io.Serializable
