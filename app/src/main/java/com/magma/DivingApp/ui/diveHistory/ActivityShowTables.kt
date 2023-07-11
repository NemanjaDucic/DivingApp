package com.magma.DivingApp.ui.diveHistory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView



import com.magma.DivingApp.databinding.ActivityShowTablesBinding

import java.io.File
import java.io.FileOutputStream

class ActivityShowTables:AppCompatActivity() {
    private lateinit var binding:ActivityShowTablesBinding
    private lateinit var pdfView: PDFView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    var string:String ?= "navitables.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTablesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {

        pdfView = binding.pdfView
        searchButton = binding.searchButton
        searchEditText = binding.searchEditText
        string = intent.getStringExtra("selectedString")
        val fileName = string // Replace with your PDF file name
        val assetManager = applicationContext.assets
        val inputStream = assetManager.open(fileName!!)
        val outputFile = File(filesDir, fileName)

        FileOutputStream(outputFile).use { output ->
            val buffer = ByteArray(4 * 1024) // Adjust buffer size as per your requirement
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }


        val pdfFileName = string
        val file = getFileStreamPath(pdfFileName)

        pdfView.fromFile(file)
            .onLoad {

            }
            .load()
        searchButton.setOnClickListener {

        }
    }

}