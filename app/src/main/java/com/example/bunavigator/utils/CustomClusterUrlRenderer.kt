package com.example.bunavigator.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.bunavigator.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import kotlinx.coroutines.*

class CustomClusterUrlRenderer(val context: Context, map: GoogleMap, val clusterManager: ClusterManager<CustomMarker>):
    DefaultClusterRenderer<CustomMarker>(context,map,clusterManager){
    val iconGenerator: IconGenerator=IconGenerator(context.applicationContext)
    lateinit var imageView: ImageView
    lateinit var linearLayout: LinearLayout
    init {
//        imageView= ImageView(context.applicationContext)
//        val markerSize=context.resources.getDimension(R.dimen.marker_dimen).toInt()
//        val markerPadding=context.resources.getDimension(R.dimen.marker_padding).toInt()
//
//
//
//        imageView.layoutParams= ViewGroup.LayoutParams(markerSize,markerSize)
//
//        imageView.setPadding(markerPadding,markerPadding,markerPadding,markerPadding)
//        iconGenerator.setContentView(imageView)

        linearLayout= LinearLayout(context.applicationContext)
        linearLayout.orientation=LinearLayout.VERTICAL
        linearLayout.layoutParams= LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.location_icon,linearLayout)









    }

    override fun onBeforeClusterItemRendered(item: CustomMarker, markerOptions: MarkerOptions) {



                    if (!linearLayout.isLaidOut()) {
                        val measureSpec =
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        linearLayout.measure(measureSpec, measureSpec)
                        linearLayout.layout(
                            0,
                            0,
                            linearLayout.layoutParams.width,
                            linearLayout.layoutParams.height
                        )
                    }
        linearLayout.findViewById<TextView>(R.id.tv_location_name).text = item.title
                    val bitmapContainer = Bitmap.createBitmap(
                        linearLayout.measuredWidth,
                        linearLayout.measuredHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmapContainer)
                    linearLayout.draw(canvas)

                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapContainer))
                        .title(item.title)






//        val icon= iconGenerator.makeIcon()


    }









    override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<CustomMarker>?) {
        super.setOnClusterItemClickListener(listener)
    }



}