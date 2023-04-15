package com.example.bunavigator.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bunavigator.databinding.ActivityQractivityBinding

class QrActivity : AppCompatActivity() {
    lateinit var binding:ActivityQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val cameraPermissionLauncher=
            registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->
                when(isGranted){
                    true->{

                    }
                    else->{

                    }
                }
            }

        val writePermissionLauncher=
            registerForActivityResult(ActivityResultContracts.RequestPermission()){
                    isGranted->
                when(isGranted){
                    true->{

                    }
                    else->{

                    }
                }
            }
        binding.btnScanQR.setOnClickListener {

            when{
            allPremissionsGranted()->{
            startActivity(Intent(this, QrScanActivity::class.java))
                finish()
        }
              checkRationale()->{
                  toastMessage("RATIONALE")
              }
                else->{
                    ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                    ,1
                    )
                }
        }

        }





    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==1){
            if(grantResults.isNotEmpty()){

            if(grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED){
                toastMessage("DONE")
            }
                else{
                    if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        toastMessage("Camera permission request was declined")
                    }
                else{
                    toastMessage("Write permission request was declined")
                    }
            }


            }
        }

    }
    fun allPremissionsGranted(): Boolean {

        return ((ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
                ==PackageManager.PERMISSION_GRANTED)&&
                (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED))

    }

    private fun checkRationale():Boolean{

       if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
           ||shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
       toastMessage("You need to give this app access to your camera.")
          return true
       }

        return false
    }


    fun toastMessage(message:String){
        Toast.makeText(this,message,
            Toast.LENGTH_SHORT).show()
    }
}