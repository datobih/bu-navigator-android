package com.example.bunavigator.presentation

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.bunavigator.Constants
import com.example.bunavigator.databinding.ActivityQrScanBinding
import com.example.bunavigator.models.Destination
import com.example.bunavigator.utils.BarcodeAnalyzer
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QrScanActivity : AppCompatActivity() {
    lateinit var binding: ActivityQrScanBinding
    lateinit var cameraExecutor: ExecutorService
    var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera()


        binding.btnTakePicture.setOnClickListener {
            takePhoto()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also { imageAnalysis ->
                    imageAnalysis.setAnalyzer(cameraExecutor, BarcodeAnalyzer{
                    destination->
//                    mainViewModel.addDestination(destination)

                        Constants.mainViewModel!!.destinationSavedMutableLiveData.value=true
                        setResult(0)
                        finish()
                    })
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (e: java.lang.Exception) {
                Log.e("STARTCAMERA:", "Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(this))


    }

    fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat("yyyy.MM.dd").format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }


        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                    Toast.makeText(
                        this@QrScanActivity,
                        msg, Toast.LENGTH_SHORT
                    ).show()
                    Log.d("onImageSaved", "onImageSaved: $msg ")


                }

                override fun onError(exception: ImageCaptureException) {
                    val msg = "Something went wrong"
                    Toast.makeText(
                        baseContext,
                        msg, Toast.LENGTH_SHORT
                    ).show()
                    Log.d("onError", "onError: $msg ")

                }

            }
        )


    }


}