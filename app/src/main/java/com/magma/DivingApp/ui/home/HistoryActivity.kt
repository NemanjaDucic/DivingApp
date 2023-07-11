package com.magma.DivingApp.ui.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.database.*
import com.google.gson.Gson
import com.magma.DivingApp.adapters.HistoryAdapter

import com.magma.DivingApp.adapters.HorizontalAdapter
import com.magma.DivingApp.databinding.ActivityHistoryBinding
import com.magma.DivingApp.interfaces.HistoryDiveSelected
import com.magma.DivingApp.interfaces.HorizontalClickInterface
import com.magma.DivingApp.model.DiveModel
import com.magma.DivingApp.ui.diveHistory.DiveActivityDetails

class HistoryActivity:AppCompatActivity(),HistoryDiveSelected,HorizontalClickInterface {
    val databaseInstance: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
    private var interstitialAd: InterstitialAd? = null

    private var flag = false

     val AD_UNIT_ID = "ca-app-pub-8589992377871541/6526222500"
    private val gson = Gson()
    lateinit var rvVertical :RecyclerView
    lateinit var rvHorizontal:RecyclerView
    private lateinit var verticalAdapter: HistoryAdapter
    private lateinit var horizontalAdapter: HorizontalAdapter
    private val filterArray = arrayListOf("All Dives","This Week","This Month","This Year")
    private lateinit var binding :ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )
        if (flag == false) {
            loadAd()
            flag = true
        }

        binding.backbutton.setOnClickListener {
            showInterstitial()
        }

    }
    private fun loadAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "")
                    interstitialAd = null
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    Toast.makeText(
                        this@HistoryActivity,
                        "onAdFailedToLoad() with error $error",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    interstitialAd = ad
                    showInterstitial()
                }
            }
        )
    }

    private fun showInterstitial() {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }
            interstitialAd?.show(this)
        } else {
            Toast.makeText(this, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()

        }
    }
    private fun init(){
        val sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var id = sharedPreferences?.getString("userID","")!!


        rvVertical = binding.verticalRV
        rvHorizontal = binding.horizontalRV
        verticalAdapter = HistoryAdapter(arrayListOf(),this)
        horizontalAdapter = HorizontalAdapter(filterArray,this)
        rvVertical.adapter = verticalAdapter
        rvHorizontal.adapter = horizontalAdapter
        rvHorizontal.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        rvVertical.layoutManager = LinearLayoutManager(this)
        databaseInstance.child(id).child("dives")   .addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var temArray = arrayListOf<DiveModel>()
                for (l in dataSnapshot.children) {
                    val jsonKey = gson.toJson(l.key)
                    val json = gson.toJson(l.value)
                    val data = Gson().fromJson(json, DiveModel::class.java)
                    temArray.add(data)
                }
                verticalAdapter.setDiveHistory(temArray)
            }

            override fun onCancelled(error: DatabaseError) {

                println("Error")
            }
        })

//        binding.backbutton.setOnClickListener {
//            onBackPressed()
//        }
    }

    override fun diveSelected(dive: DiveModel) {
        println(dive)
        val intent = Intent(this, DiveActivityDetails::class.java)
        intent.putExtra("selectedDive",dive.uid)
        startActivity(intent)
    }

    override fun itemSelected(item: String,position:Int) {
        when (item){
            "All Dives" -> {
                println("All Dives")
            }
            "This Week" -> {
                println("All week")
            }
            "This Month" -> {
                println("All month")
            }
            "This Year" -> {
                println("All year")
            }

        }
        println(position)
    }


}