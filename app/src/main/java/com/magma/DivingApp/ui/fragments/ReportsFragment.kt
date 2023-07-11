package com.magma.DivingApp.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magma.DivingApp.R
import com.magma.DivingApp.adapters.ReportsAdapter
import com.magma.DivingApp.databinding.FragmentReportsBinding
import com.magma.DivingApp.interfaces.TableClickedInterface
import com.magma.DivingApp.ui.diveHistory.ActivityShowTables
import com.mapbox.maps.extension.style.expressions.dsl.generated.at
import java.io.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ReportsFragment:Fragment(),TableClickedInterface {
    private lateinit var binding: FragmentReportsBinding
    private val list = arrayListOf("Air Decompression Table","Surface-Supplied Helium-Oxygen Decompression Table","1.3 ata ppO2 N2O2 Decompression Table.","1.3 ata ppO2 HeO2 Decompression Tables","Closed-Circuit Mixed-Gas UBA Decompression Table Using 0.75 ata ppO2 N2O2","Closed-Circuit Mixed-Gas UBA Decompression Table Using 0.75 ata Constant Partial Pressure Oxygen in Helium","All Tabels")
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: ReportsAdapter
    private lateinit var attachedContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachedContext = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentReportsBinding.inflate(inflater)
        init()
        recyclerView = binding.recyclerViewReports
        adapter = ReportsAdapter(arrayListOf(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(attachedContext)
        adapter.setList(list)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        val currentDate = LocalDate.now()
        val year = currentDate.year
        val month = currentDate.monthValue
        val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault())
        val formated = currentDate.format(monthFormatter)
        binding.datedateTV.text = formated + year.toString()
    }

    override fun viewClicked(whichView: String) {
        val intent = Intent(attachedContext,ActivityShowTables::class.java)
        when (whichView){
            "0" -> {
                intent.putExtra("selectedString","99.pdf")
            }
            "1" -> {
                intent.putExtra("selectedString","124.pdf")
            }
            "2" -> {
                intent.putExtra("selectedString","1510.pdf")
            }
            "3" -> {
                intent.putExtra("selectedString","1513.pdf")
            }
            "4" -> {
                intent.putExtra("selectedString","1516.pdf")
            }
            "5" -> {
                intent.putExtra("selectedString","1517.pdf")
            }
            "6" -> {
                intent.putExtra("selectedString","navitables.pdf")
            }
        }

        startActivity(intent)
    }

    override fun dlClicked(dl: String) {
        when (dl){
            "0" -> {
                copyAssets("99.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }            }
            "1" -> {
                copyAssets("124.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }            }
            "2" -> {
                copyAssets("1510.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }            }
            "3" -> {
                copyAssets("1513.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }            }
            "4" -> {
                copyAssets("1516.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }            }
            "5" -> {
                copyAssets("1517.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }
            }
            "6" -> {
                copyAssets("navitables.pdf").let {
                    Toast.makeText(attachedContext,"Table Sucesfully Downloaded ,check your downloads folder",Toast.LENGTH_SHORT ).show()
                }
            }
        }

        }

    private fun copyAssets(file:String) {
        val assetManager: AssetManager = attachedContext.assets
        val filename = file// Replace with the desired specific file name

        try {
            assetManager.open(filename).use { `in` ->
                val outDir = Environment.getExternalStorageDirectory().absolutePath + "/Download/"
                val outFile = File(outDir, filename)
                FileOutputStream(outFile).use { out ->
                    copyFile(`in`, out)
                }
            }
        } catch (e: IOException) {
            println("Failed to copy asset file: $filename")
        }
    }
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

}