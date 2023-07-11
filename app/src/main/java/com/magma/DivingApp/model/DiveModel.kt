package com.magma.DivingApp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.io.Serializable
@Parcelize
data class DiveModel(
    var diveType:String ?= "",
    var diveReportNumber: String ?= "",
    var client:String ?= "",
    var uid:String ?= "",
    var date:String ?= "",
    var vessel:String ?= "",
    var current:String ?= "",
    var swell:String ?= "",
    var visibility:String ?= "",
    var waterTemperature:String ?= "",
    var location : String ?= "",
    var divingMethod : String ?= "",
    var bottomMix : String ?= "",
    var bottomMixAnalysis : String ?= "",
    var superintendent:String ?=  "",
    var supervisor : String ?= "",
    var diver1:String ?= "",
    var diver2 :String ?= "",
    var sByDriver:String ?= "",
    var winchOP:String ?= "",
    var chamberOP:String ?= "",
    var diver1HeadGear:String ?= "",
    var diver1BallOut:String ?= "",
    var diver1Suit:String ?= "",
    var sByHeadGear :String ?= "",
    var sByBallOut:String ?= "",
    var sBySuit:String ?= "",
    var accident:String ?= "",
    var accidentReportNumber:String ?= ""

) : Serializable, Parcelable