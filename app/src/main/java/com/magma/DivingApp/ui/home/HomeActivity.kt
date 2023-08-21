package com.magma.DivingApp.ui.home

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.magma.DivingApp.R
import com.magma.DivingApp.databinding.ActivityTabBarHolderBinding
import com.magma.DivingApp.ui.RegisterActivity
import com.magma.DivingApp.ui.fragments.HomeFragment
import com.magma.DivingApp.ui.fragments.NotificationsFragment
import com.magma.DivingApp.ui.fragments.ReportsFragment

class HomeActivity:AppCompatActivity() {
    private lateinit var binding: ActivityTabBarHolderBinding
    lateinit var bottomNav : BottomNavigationView
    private var rewardedAd: RewardedAd? = null
    val AD_UNIT_ID = "ca-app-pub-8589992377871541~9946538868"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBarHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("userID","")
        if (user!!  == ""){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        } else {
            init()
            var num = sharedPreferences.getInt("adReward",1)
            if (num % 2 == 0){
                loadRewardedAd()
            } else {
                sharedPreferences.edit().putInt("adReward",num+1).apply()
            }
        }


    }
    private fun loadRewardedAd() {
        if (rewardedAd == null) {
            var adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        println(adError?.message)
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(ContentValues.TAG, "Ad was loaded.")
                        rewardedAd = ad
                        showRewardedVideo()
                    }
                }
            )
        }
    }
    private fun showRewardedVideo() {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(ContentValues.TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null

                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(ContentValues.TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }

            rewardedAd?.show(
                this,
                OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type

                    Log.d("TAG", "User earned the reward.")
                }
            )
        }
    }
    private fun init(){
        val actionbar = supportActionBar
        actionbar?.title = ""
        bottomNav = binding.bottomNav
        actionbar?.setDisplayHomeAsUpEnabled(true)
        loadFragment(HomeFragment())
        val readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this, readPermission) == PackageManager.PERMISSION_GRANTED )
            // Permissions are already granted, proceed with your code
         else {
            // Permissions are not granted, request them
            ActivityCompat.requestPermissions(this, arrayOf(readPermission), 200)
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menu_reports -> {
                    loadFragment(ReportsFragment())
                    true
                }

                R.id.menu_notifications -> {
                    loadFragment(NotificationsFragment())
                    true
                }
                R.id.menu_profile -> {
                    loadFragment(com.magma.DivingApp.ui.fragments.ProfileFragment())
                    true
                }

                else -> {
                   loadFragment(HomeFragment())
                    true
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"With this You can Now Downlaod Tables",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"IF you want to Downlaod Tables grant permissions from settings",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}