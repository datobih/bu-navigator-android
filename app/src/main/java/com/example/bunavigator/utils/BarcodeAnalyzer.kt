package com.example.bunavigator.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.bunavigator.models.Destination
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(val scanDone: (Destination) -> Unit) : ImageAnalysis.Analyzer {
    val gson = Gson()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.also {
            val image = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )

            val scanner = BarcodeScanning.getClient()
            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    Log.d("BARCODE", "analyze: Success!")

                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue
                        if (!rawValue.isNullOrEmpty()) {

                            Log.d("BARCODE", "analyze: Success $rawValue")
                            try {
                                val destination = gson.fromJson(rawValue, Destination::class.java)
                                Log.d("BARCODE", "This is dest $destination")
                                scanDone(destination)

                            }
                            catch (exception:java.lang.IllegalStateException){
                                Log.d("BARCODE", "analyze: Wrong Scan")
                            }


                        }

                    }
                }
                .addOnFailureListener {
                    Log.d("BARCODE", "analyze:  Failure")
                }

        }
        Thread.sleep(1500)
        imageProxy.close()
    }
}