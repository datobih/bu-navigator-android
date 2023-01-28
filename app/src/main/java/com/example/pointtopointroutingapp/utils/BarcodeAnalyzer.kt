package com.example.pointtopointroutingapp.utils

import android.annotation.SuppressLint
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

                }
                .addOnFailureListener {

                }

        }

    }
}