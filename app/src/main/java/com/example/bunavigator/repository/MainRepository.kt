package com.example.bunavigator.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.bunavigator.Constants

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.bunavigator.models.Destination
class MainRepository(private val sharedPreferences: SharedPreferences) {
val gson=Gson()
    fun isUserFirstTime():Boolean{

        return sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_FIRST_TIME,true)

    }

    fun onBoardingDone(){

        val destinations=Constants.buDestinations
        val jsonDest=gson.toJson(destinations)
        Log.d("BU_DESTINATION", jsonDest)

        sharedPreferences.edit {
            putBoolean(Constants.SHARED_PREFERENCES_FIRST_TIME,false)
            putString(Constants.SHARED_PREFERENCES_LOCATIONS,jsonDest)
            commit()
        }

    }

    fun getDestinations():List<Destination>{
        val destinationsJson= sharedPreferences.getString(Constants.SHARED_PREFERENCES_LOCATIONS,"")!!
        val destinations=gson.fromJson<List<Destination>>(destinationsJson,object : TypeToken<List<com.example.bunavigator.models.Destination>>(){}.type)
        return  destinations
    }

    fun addDestination(destination:Destination){
        val destinations= ArrayList<Destination>(getDestinations())
        destinations.add(destination)
        val destinationsJson=gson.toJson(destinations.toList())
        sharedPreferences.edit(commit = true) {
            putString(Constants.SHARED_PREFERENCES_LOCATIONS, destinationsJson)
        }


    }

data class DestinationData(val destinationList: List<com.example.bunavigator.models.Destination>)

}