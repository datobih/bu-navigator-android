package com.example.pointtopointroutingapp.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.pointtopointroutingapp.Constants

class MainRepository(private val sharedPreferences: SharedPreferences) {

    fun isUserFirstTime():Boolean{

        return sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_FIRST_TIME,true)

    }

    fun onBoardingDone(){

        sharedPreferences.edit {
            putBoolean(Constants.SHARED_PREFERENCES_FIRST_TIME,false)
            commit()
        }

    }



}