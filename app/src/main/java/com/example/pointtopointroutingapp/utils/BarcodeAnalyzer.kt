package com.example.pointtopointroutingapp.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer():ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage= imageProxy.image
        mediaImage?.also {
            val image= InputImage.fromMediaImage(it,
            imageProxy.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()
            val  result= scanner.process(image)
                .addOnSuccessListener {
                    barcodes->
                    Log.d("BARCODE", "analyze: Success!")

                    for(barcode in barcodes){
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue
                        Log.d("BARCODE", "analyze: Success $rawValue")

                    }
                }
                .addOnFailureListener {
                    Log.d("BARCODE", "analyze:  Failure")
                }

        }

        imageProxy.close()
    }
}