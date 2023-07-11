package com.magma.DivingApp.ui.createNewDive

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.magma.DivingApp.R
import com.magma.DivingApp.helpers.Utilities
import com.magma.DivingApp.databinding.ActivityNewDiveDetailsBinding
import com.magma.DivingApp.model.DiveModel
import com.magma.DivingApp.showToast
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.search.autofill.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewDiveDetailsActivity:AppCompatActivity() {
    private lateinit var binding:ActivityNewDiveDetailsBinding
    private var dive = DiveModel()

    private val bundle = Bundle()
    private lateinit var mapView: MapView
    private lateinit var addressAutofill: AddressAutofill
    private lateinit var mapboxMap: MapboxMap
    private var ignoreNextMapIdleEvent: Boolean = false
    private val vesselOptions = arrayListOf(
        "DSV Huwaila",
        "DSV Shaddad",
        "Halul 43",
        "Halul 51",
        "DSV Al Ghariyah",
        "DSV Seven Atlantic",
        "DSV Seven Falcon",
        "DSV Seven Pelican",
        "DSV Skandi Arctic",
        "DSV Skandi Singapore",
        "DSV Skandi Achiever",
        "DSV Skandi Achiever II",
        "DSV Skandi Constructor",
        "CCC Pioneer",
        "CCC Supporter",
        "Dulam Explorer",
        "Dulam Unity",
        "Topaz Tangaroa",
        "Topaz Tiamat",
        "Topaz Thunder",
        "Topaz Triumph",
        "Topaz Rayyan",
        "Topaz Caspian",
        "POSH Xanadu: DSV",
        "POSH Endurance: DSV",
        "POSH Serenade: DSV",
        "POSH Aria: DSV",
        "POSH Arcadia: DSV",
        "POSH Mogami: DSV",
        "POSH Mogul: DSV",
        "POSH Semco: DSV",
        "POSH Fawn: DSV",
        "POSH Eagle: DSV",
        "Swiber Else-Marie: DSV",
        "Swiber Lavenir: DSV",
        "Swiber Atlantis: DSV",
        "Pacific Responder: DSV",
        "Pacific Patron: DSV",
        "Pacific Blade: DSV",
        "Seven Atlantic: DSV",
        "Seven Pelican: DSV",
        "Seven Falcon: DSV",
        "Seven Havila: DSV",
        "Skandi Arctic: DSV",
        "Skandi Singapore: DSV",
        "Skandi Achiever: DSV",
        "Skandi Achiever II: DSV",
        "Bibby Topaz: DSV",
        "Bibby Sapphire: DSV"

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDiveDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dive = intent.getSerializableExtra("dive") as DiveModel
        listeners()
        mapView = binding.mapView
        binding.locationET.isFocusable = false
        binding.dateET.isFocusable = false
        binding.vesselSpinner.adapter  = ArrayAdapter(this, android.R.layout.simple_list_item_1, vesselOptions)
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val current = LocalDateTime.now().format(formatter)
        binding.dateET.setText(current)
        mapboxMap = mapView.getMapboxMap()
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        addressAutofill = AddressAutofill.create(getString(R.string.mapbox_access_token))
        mapboxMap.addOnMapIdleListener {
            if (ignoreNextMapIdleEvent) {
                ignoreNextMapIdleEvent = false
                return@addOnMapIdleListener
            }

            val mapCenter = mapboxMap.cameraState.center
            findAddress(mapCenter)
        }


    }
    private fun listeners(){
        binding.backbutton.setOnClickListener {
            onBackPressed()
        }
        binding.locationET.setOnClickListener {
        }
        binding.button.setOnClickListener {
            if (check()) {
                dive.location = binding.locationET.text.toString()
                dive.diveReportNumber = binding.divereportnumET.text.toString()
                dive.client = binding.clientET.text.toString()
                dive.current = binding.currentET.text.toString()
                dive.swell = binding.swelltET.text.toString()
                dive.date = binding.dateET.text.toString()
                dive.visibility = binding.visibilityET.text.toString()
                dive.waterTemperature = binding.watertempET.text.toString()
                dive.vessel = binding.vesselSpinner.selectedItem.toString()
                bundle.putSerializable("dive",dive)
                Utilities.intent(this,NewDiveDivingMethodActivity::class.java,bundle)

            }
            else {
                Toast.makeText(this,"Please Fill in All the Fields", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun findAddress(point: Point) {
        lifecycleScope.launchWhenStarted {
            val response = addressAutofill.suggestions(point, AddressAutofillOptions())
            response.onValue { suggestions ->
                if (suggestions.isEmpty()) {
                    println("empty")
                } else {
                    selectSuggestion(
                        suggestions.first(),
                        fromReverseGeocoding = true
                    )
                }
            }.onError {
                showToast("ssss")
            }
        }
    }
    private fun selectSuggestion(suggestion: AddressAutofillSuggestion, fromReverseGeocoding: Boolean) {
        lifecycleScope.launchWhenStarted {
            val response = addressAutofill.select(suggestion)
            response.onValue { result ->
                showAddressAutofillResult(result, fromReverseGeocoding)
            }.onError {
                showToast("Hold long click on map to select Destination")
            }
        }
    }
    private fun showAddressAutofillResult(result: AddressAutofillResult, fromReverseGeocoding: Boolean) {
        val address = result.address
        binding.locationET.setText(address.country + "," + address.locality)

        if (!fromReverseGeocoding) {
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(result.suggestion.coordinate)
                    .zoom(16.0)
                    .build()
            )

        }
    }

    private fun check () :Boolean{
        val editTextList = listOf(
            binding.divereportnumET,
            binding.clientET,
            binding.currentET,
            binding.swelltET,
            binding.currentET,
            binding.visibilityET,
            binding.watertempET,
            binding.locationET
        )

        for (editText in editTextList) {
            when {
                editText.text.toString().isEmpty() -> return false

            }
        }

        return true
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

}