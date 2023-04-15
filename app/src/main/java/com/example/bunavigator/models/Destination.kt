package com.example.bunavigator.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Destination(
    val name: String,
    val latitude: Double,
    val longitude: Double, val markerRes: Int=0,
    val imageUrl:String="",
    val dest:String=""
) : Parcelable
