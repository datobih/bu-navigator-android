package com.example.pointtopointroutingapp.utils

import android.graphics.Bitmap
import android.graphics.drawable.Icon
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CustomMarker(
    private var position: LatLng,
    private var title: String,
    private var snippet:String,
    private var icon:Int=0,
    private var imageUrl:String="",
    private var bitmap:Bitmap?= null
) : ClusterItem {


    override fun getPosition(): LatLng {
        return position
    }


    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun getIcon():Int{
        return icon
    }

    fun getIconUrl():String{
        return imageUrl
    }

    fun setSnippet(snippet: String){
        this.snippet=snippet
    }


}