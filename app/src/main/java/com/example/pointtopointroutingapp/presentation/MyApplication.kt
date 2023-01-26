package com.example.pointtopointroutingapp.presentation

import android.app.Application
import android.content.Context
import com.example.pointtopointroutingapp.Constants

class MyApplication():Application() {
    override fun getApplicationContext(): Context {
        val context=super.getApplicationContext()
        Constants.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        return context
    }
}