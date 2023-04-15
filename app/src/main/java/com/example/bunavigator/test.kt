package com.example.bunavigator

import com.example.bunavigator.models.Destination
import com.google.gson.Gson
import java.math.BigDecimal

fun main() {

val  gson= Gson()

    var tes=Destination("RRFFE",12.3,343.5, imageUrl = "DD",)

//    val data="{\"name\":\"Diamond Hall\",\"latitude\":6.8917484,\"longitude\":3.7269755,\"imageUrl\":\"https://i.postimg.cc/sD9W5jQn/IMG-20230214-165659.jpg\"}"

//    {"name":"Diamond Hall","latitude":6.8917484,"longitude":3.7269755,"imageUrl":"https://i.postimg.cc/sD9W5jQn/IMG-20230214-165659.jpg"}

//    val res=gson.toJson(tes)
//    print(res)
//    tes=gson.fromJson(data,Destination::class.java)
//    print(tes)
}

fun round(double: Double):Double{
    return (double*100.0)/100.0
}
