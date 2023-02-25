package com.example.pointtopointroutingapp.models

data class Destination(
    val name: String,
    val latitude: Double,
    val longitude: Double, val markerRes: Int=0,
    val imageUrl:String="",
    val dest:String=""
)
