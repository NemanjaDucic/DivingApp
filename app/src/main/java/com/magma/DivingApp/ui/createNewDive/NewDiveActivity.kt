package com.magma.DivingApp.ui.createNewDive

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.magma.DivingApp.helpers.Utilities
import com.magma.DivingApp.databinding.ActivityNewDiveBinding
import com.magma.DivingApp.model.DiveModel
import com.skydoves.balloon.*
import com.magma.DivingApp.R;

class NewDiveActivity:AppCompatActivity() {
    private lateinit var binding : ActivityNewDiveBinding
    private lateinit var locationManager: LocationManager

    private var dive = DiveModel()
    private val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        getLocation()
    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
    private fun listeners(){
        binding.firstInfoButton.setOnClickListener {
            binding.firstInfoButton.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
            val intent = Intent(this,AddressAutofillUiActivity::class.java)
            startActivity(intent)
        }
        binding.secondInfoButton.setOnClickListener {
            binding.secondInfoButton.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
        }
        binding.thirdInfoButton.setOnClickListener {
            binding.thirdInfoButton.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
        }
        binding.fourthInfoButton.setOnClickListener {
            binding.fourthInfoButton.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
        }
        binding.firstInfoButton5.setOnClickListener {
            binding.firstInfoButton5.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
        }
        binding.firstInfoButton6.setOnClickListener {
            binding.firstInfoButton6.showAlignBottom(
                Balloon.Builder(this)
                    .setWidthRatio(1.0f)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setTextColor(Color.BLACK)
                    .setText("randomest of desctiptions")
                    .setTextSize(15f)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowSize(10)
                    .setArrowPosition(0.5f)
                    .setPadding(12)
                    .setCornerRadius(8f)
                    .setBackgroundColor(Color.WHITE)
                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                    .setLifecycleOwner(this)
                    .build()
            )
        }
        binding.button.setOnClickListener {
            if (dive.diveType != "1"){
                    bundle.putSerializable("dive",dive)
                    Utilities.intent(this,NewDiveDetailsActivity::class.java,bundle)



            }
            else {
                    Toast.makeText(this,"Please Pick Diving Type",Toast.LENGTH_SHORT).show()
            }
        }
        binding.backbutton.setOnClickListener {
            onBackPressed()
        }
        binding.radioBut2.setOnClickListener {
            dive.diveType = "S.R.P"
            binding.radioBut1.isChecked = false
            binding.radioBut3.isChecked = false
            binding.radioBut4.isChecked = false
            binding.radioBut5.isChecked = false
            binding.radioBut6.isChecked = false
            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_blue_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_gray_bcg)

        }
        binding.radioBut3.setOnClickListener {
            dive.diveType = "Bell Bounce"
            binding.radioBut2.isChecked = false
            binding.radioBut1.isChecked = false
            binding.radioBut4.isChecked = false
            binding.radioBut5.isChecked = false
            binding.radioBut6.isChecked = false
            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_blue_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_gray_bcg)
        }
        binding.radioBut1.setOnClickListener {
            dive.diveType = "Scuba"
            binding.radioBut2.isChecked = false
            binding.radioBut3.isChecked = false
            binding.radioBut4.isChecked = false
            binding.radioBut5.isChecked = false
            binding.radioBut6.isChecked = false
            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_blue_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_gray_bcg)
        }
        binding.radioBut4.setOnClickListener {
            dive.diveType = "Wet Bell"
            binding.radioBut2.isChecked = false
            binding.radioBut3.isChecked = false
            binding.radioBut1.isChecked = false
            binding.radioBut5.isChecked = false
            binding.radioBut6.isChecked = false

            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_blue_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_gray_bcg)

        }
        binding.radioBut5.setOnClickListener {
            dive.diveType = "Surf.Supp"
            binding.radioBut2.isChecked = false
            binding.radioBut3.isChecked = false
            binding.radioBut1.isChecked = false
            binding.radioBut4.isChecked = false
            binding.radioBut6.isChecked = false

            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_blue_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_gray_bcg)
        }
        binding.radioBut6.setOnClickListener {
            dive.diveType = "T.U.P"
            binding.radioBut2.isChecked = false
            binding.radioBut3.isChecked = false
            binding.radioBut1.isChecked = false
            binding.radioBut5.isChecked = false
            binding.radioBut4.isChecked = false

            binding.lightGrayHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.blueHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.orangeHolder.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder5.setBackgroundResource(R.drawable.light_gray_bcg)
            binding.purpleHolder6.setBackgroundResource(R.drawable.light_blue_bcg)
        }
    }
}