package com.example.pointtopointroutingapp.presentation

import android.app.Application
import android.content.Context
import com.example.pointtopointroutingapp.Constants
import com.example.pointtopointroutingapp.repository.MainRepository

class MyApplication():Application() {


    override fun onCreate() {

        super.getApplicationContext()
        super.onCreate()

    }


    override fun getApplicationContext(): Context {
        val context=super.getApplicationContext()
        Constants.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        Constants.mainRepository= MainRepository(Constants.sharedPreferences!!)
        return context
    }
}