package com.example.bunavigator.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunavigator.Constants
import com.example.bunavigator.databinding.ActivityMainBinding
import com.google.android.gms.maps.*
import com.google.maps.android.clustering.ClusterManager
import com.example.bunavigator.utils.CustomMarker
import com.example.bunavigator.R
import com.example.bunavigator.adapters.DestinationRecyclerAdapter
import com.example.bunavigator.models.PolyLineData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.DirectionsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.bunavigator.adapters.LocationsRecyclerAdapter
import com.example.bunavigator.models.Destination
import com.example.bunavigator.utils.CustomClusterUrlRenderer
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class MainActivity : AppCompatActivity(),
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null
    var location: Location? = null
    lateinit var clusterManager: ClusterManager<CustomMarker>
    lateinit var geoApiContext: GeoApiContext
    var mPolyData = ArrayList<PolyLineData>()
    var mPolyline: Polyline? = null
    var selectedMarker: Marker? = null
    lateinit var destinationAdapter:DestinationRecyclerAdapter
    var previousPinPoint: Marker? = null
    private val mainViewModel:MainViewModel by viewModels { MainViewModel.Factory }

    private val startSettingsForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (checkLocation()) displayMap()
            else toastMessage("Location still not enabled")

        }
    private val startQrForResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        val savedDestination=mainViewModel.savedStateHandle.get<Destination>(Constants.SAVED_HANDLE_DESTINATION_SAVED)
//        if(savedDestination!=null) {
//
//        }

    }

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants.mainViewModel=mainViewModel

        mainViewModel.destinationSavedMutableLiveData.observe(this){

            if(it!=null){
                Toast.makeText(this, "New destination added", Toast.LENGTH_SHORT).show()
            }

        }

        val isFirstTime=mainViewModel.isUserFirstTime()
        if(isFirstTime){
            Log.d("FIRST_USER", "onCreate: TRUE")
            startActivity(Intent(this,OnboardingActivity::class.java))
            finish()
        }
        else{
            Constants.buDestinations=mainViewModel.getDestinations()
            Log.d("FIRST_USER", "onCreate: FALSE")
        }

    binding.navMain.setNavigationItemSelectedListener {

        menuItem->

       when(menuItem.itemId){
            R.id.Profile->{
                startQrForResult.launch(Intent(this,QrActivity::class.java))
                true
            }


           else->{
               true
           }
        }

    }
       destinationAdapter = DestinationRecyclerAdapter(Constants.buDestinations)
        destinationAdapter.destinationOnClickListener =
            object : DestinationRecyclerAdapter.DestinationOnClickListener {
                override fun onClick(latitude: Double, longitude: Double) {
                    setCameraView(latitude, longitude,true)
                    BottomSheetBehavior.from(binding.layoutContentMain.sheetScene.sheet).apply {
                        peekHeight = 200
                        this.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }

            }

        //Add click listeners to filter location views
        for(child in binding.layoutContentMain.llLocationFilters.children){
            child.setOnClickListener {
                Toast.makeText(this,(child as AppCompatButton).text,Toast.LENGTH_SHORT ).show()
                binding.layoutContentMain.actSearchLocation.setText((child as AppCompatButton).text)
            }
        }


        val sceneRoot= binding.layoutContentMain.sceneRoot
        val sheetScene= Scene.getSceneForLayout(sceneRoot,R.layout.sheet_scene,this)
        val locationRvScene=Scene.getSceneForLayout(sceneRoot,R.layout.location_rv_scene,this)



//
        val searchLocationQueryAdapter=LocationsRecyclerAdapter()
//        binding.layoutContentMain.rvLocations.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//        binding.layoutContentMain.rvLocations.adapter=searchLocationQueryAdapter

        binding.layoutContentMain.actSearchLocation.addTextChangedListener {

            if(it.isNullOrEmpty()){

                TransitionManager.go(sheetScene, Slide())

                sheetScene.sceneRoot.findViewById<RecyclerView>(R.id.rv_destinations).apply {
                    layoutManager=LinearLayoutManager(this@MainActivity)
                    adapter=destinationAdapter

                }


//                binding.layoutContentMain.rvLocations.visibility=View.GONE
//                binding.layoutContentMain.sceneRoot.visibility= View.VISIBLE


                setupBottomSheet(sheetScene.sceneRoot.findViewById(R.id.sheet))


            }

            else{
                locationRvScene.sceneRoot.findViewById<RecyclerView>(R.id.rv_destinations)?.apply {
                    TransitionManager.go(locationRvScene, Slide())
                }
                locationRvScene.sceneRoot.findViewById<RecyclerView>(R.id.rv_locations).apply {
                    val locationList=ArrayList<Destination>()


                for(location  in Constants.buDestinations){
                    if(location.name.lowercase().contains(it.toString().lowercase())){
                        locationList.add(location)
                    }
                }
                    layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter=searchLocationQueryAdapter
                searchLocationQueryAdapter.submitList(locationList)

                }

//                val locationList=ArrayList<Destination>()
//                binding.layoutContentMain.sceneRoot.visibility=View.GONE
//                binding.layoutContentMain.rvLocations.visibility=View.VISIBLE
//
//                for(location  in Constants.destinations){
//                    if(location.name.contains(it.toString())){
//                        locationList.add(location)
//                    }
//                }
////                binding.layoutContentMain.rvLocations.adapter=searchLocationQueryAdapter
//                searchLocationQueryAdapter.submitList(locationList)
            }


        }

        binding.drawerMain.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        setSupportActionBar(binding.layoutContentMain.mainActionBar)
//        supportActionBar?.setIcon(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.layoutContentMain.mainActionBar.setNavigationOnClickListener {

            if(!binding.drawerMain.isDrawerOpen(GravityCompat.START)) {
                binding.drawerMain.openDrawer(GravityCompat.START)
            }
            else binding.drawerMain.closeDrawer(GravityCompat.START)

        }


        binding.layoutContentMain.btnRefreshMap.setOnClickListener {
            resetMap()
        }
        val actSearchLocation=binding.layoutContentMain.actSearchLocation
        actSearchLocation.setOnTouchListener { v, event ->
            if(event.action==MotionEvent.ACTION_UP){
                val drawable= actSearchLocation.compoundDrawables.get(2)
                if(drawable!=null && event.rawX>=(actSearchLocation.right-drawable.bounds.width())){
                   resetMap()
                    actSearchLocation.clearFocus()
                    val imm= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(actSearchLocation.windowToken,0)
                    return@setOnTouchListener true
                }

            }
            return@setOnTouchListener false
        }



        setupBottomSheet(binding.layoutContentMain.sheetScene.sheet)


        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "ACCESS GRANTED", Toast.LENGTH_SHORT).show()
                    validateMapRequirement()
                } else {
                    Toast.makeText(this, "ACCESS DENIED", Toast.LENGTH_SHORT).show()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                validateMapRequirement()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {

            }
            else -> {
                Toast.makeText(this, "BLAH BLAH BLAH", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }


        }


    }




    @SuppressLint("MissingPermission")
    fun displayMap() {
        if (mapFragment == null) {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            val locationCallback=object:LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    val mLocation=locationResult.lastLocation
                    location=mLocation
                    Log.d("LASTLOCATION", "displayMap: ${mLocation!!.latitude}")
//                    Toast.makeText(this@MainActivity,"lat ${mLocation.latitude}",Toast.LENGTH_SHORT).show()

                    selectedMarker?.apply {

                        val desLat=position.latitude
                        val desLon=position.longitude
                        val bigLat=BigDecimal("$desLat")
                        val bigOriginLat=BigDecimal("${mLocation.latitude}")
                        val diffLat=bigLat.subtract(bigOriginLat).abs()

                        val bigLon=BigDecimal("$desLon")
                        val bigOriginLon=BigDecimal("${mLocation.longitude}")
                        val diffLon=bigLon.subtract(bigOriginLon).abs()

//                        Toast.makeText(this@MainActivity,"lat ${diffLat} lon ${diffLon}",Toast.LENGTH_SHORT).show()
                        if(diffLat<=BigDecimal("0.0000600") && diffLon<=(BigDecimal("0.0000600"))){
                            Toast.makeText(this@MainActivity,"You are at your destination",Toast.LENGTH_SHORT).show()
                            resetMap()
                        }
                    }



                }
            }
            val locationRequest= LocationRequest.
            Builder(Priority.PRIORITY_HIGH_ACCURACY,3000).build()

            mFusedLocationClient!!.requestLocationUpdates(locationRequest,locationCallback,
                Looper.getMainLooper())

            mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    location = task.result
                    if (mMap != null) {
                        setCameraView()
                        setupClusterer(Constants.buDestinations)
                    }
                    Log.d("Latitude", "displayMap: ${location!!.latitude-6.890618}")
                    Log.d("Longitude", "displayMap ${location!!.longitude-3.722744}")


                }
            }
            val options = GoogleMapOptions()
            mapFragment = SupportMapFragment.newInstance(options)
            supportFragmentManager.beginTransaction().replace(R.id.map, mapFragment!!).commit()
            mapFragment!!.getMapAsync(this)


        }
    }



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(
//            MarkerOptions().position(sydney)
//                .title("Marker in Sydney")
//        )


        mMap.setMapStyle(MapStyleOptions(Constants.mapStyle))
        if (location != null) {
            setCameraView()
            setupClusterer(Constants.buDestinations)
        }
        mMap.isMyLocationEnabled = true
        //    41.26443
//    -95.94438
        mMap.setOnCameraMoveListener {
            val cameraPos = mMap.cameraPosition
            val latitude = cameraPos.target.latitude
            val longitude = cameraPos.target.longitude
            if ((latitude < 6.9 && latitude > 6.5) && (longitude > 3.5 && longitude < 3.8)) {
                clusterManager.cluster()
            }

        }

        mMap.setOnPolylineClickListener {

            mPolyline?.color = ContextCompat.getColor(this, R.color.darkGrey)
            mPolyline?.zIndex = 0F
            it.color = ContextCompat.getColor(this, R.color.mBlue)
            mPolyline = it
            it.zIndex = 1F
            val polyData = mPolyData.find { it.polyline == mPolyline }


            selectedMarker!!.isVisible = false
            previousPinPoint?.remove()
            previousPinPoint = mMap.addMarker(
                MarkerOptions().position(selectedMarker!!.position)
                    .snippet(polyData!!.leg.duration.toString())
                    .title(selectedMarker!!.title)
            )


        }
//        mMap.setOnInfoWindowClickListener {
//            calculateDirection(it)
//        }
        geoApiContext = GeoApiContext.Builder()
            .apiKey("AIzaSyBxJjluHe9hdu781KTZfn_6i0CkE47780Q")
            .build()


        binding.layoutContentMain.sheetScene.rvDestinations.layoutManager = LinearLayoutManager(this)
        binding.layoutContentMain.sheetScene.rvDestinations.adapter = destinationAdapter


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.hamburger_menu ->{

                true
            }


            else->super.onOptionsItemSelected(item)
        }



    }



    fun toastMessage(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    fun validateMapRequirement() {
        if (checkLocation()) displayMap()
        else startSettingsForResult.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }


    fun checkLocation(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    }



    private fun calculateDirection(marker: Marker) {
        val TAG = "calculateDirections"
        val destination = com.google.maps.model.LatLng(
            marker.position.latitude,
            marker.position.longitude
        )
        val directions = DirectionsApiRequest(geoApiContext)
        directions.alternatives(true)
        directions.origin(
            com.google.maps.model.LatLng(
                location!!.latitude,
                location!!.longitude
            )
        )
        directions.destination(destination).setCallback(
            object : PendingResult.Callback<DirectionsResult> {
                override fun onResult(result: DirectionsResult?) {
                    Log.d(TAG, result!!.routes[0].toString())
                    Log.d(TAG, result!!.routes[0].legs[0].duration.toString())
                    Log.d(TAG, result!!.routes[0].legs[0].distance.toString())


                    showPolylineToMap(result!!)
                }

                override fun onFailure(e: Throwable?) {
                    Log.d(TAG, "ERRORRRR ${e!!.message}")
                }

            }
        )

    }
//    41.26443
//    -95.94438
    private fun setCameraView(

        latitude: Double = 6.8907522,
        longitude: Double = 3.7217974,
//        latitude: Double = 6.894475,
//        longitude: Double = 3.724666,
        zoomIn:Boolean=false
    ) {


        val bottomBoundary = longitude!! - 0.1
        val leftBoundary = latitude!! - 0.1
        val topBoundary = longitude!! + 0.1
        val rightBoundary = latitude!! + 0.1
        val mapBoundary =
            LatLngBounds(LatLng(leftBoundary, bottomBoundary), LatLng(rightBoundary, topBoundary))

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBoundary, 0))
        if(!zoomIn) mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        else {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(19f))
            clusterManager?.cluster()
        }

    }


    fun resetMap() {

//        mMap?.apply {
//            clear()
//        }
//
//        clusterManager?.apply {
//            clearItems()
//        }
        for(polyData in mPolyData.map { it.polyline }){
            polyData.remove()
        }





        mPolyData.clear()
        mPolyline = null
        previousPinPoint?.remove()
        selectedMarker?.isVisible=true
        selectedMarker = null
//        previousPinPoint = null
//        setupClusterer()

    }


    fun setupClusterer(destinations:List<Destination>) {
        clusterManager = ClusterManager<CustomMarker>(this, mMap)
        clusterManager.renderer = CustomClusterUrlRenderer(this, mMap, clusterManager)

        val collection = clusterManager.markerCollection

        collection.setOnInfoWindowClickListener {


            showAlertDialog(it)
        }




        for (destination in destinations) {
            clusterManager.addItem(
                CustomMarker(
                    LatLng(
                        destination.latitude,
                        destination.longitude
                    ), destination.name, destination.dest, imageUrl = destination.imageUrl
                )
            )

        }
        clusterManager.setAnimation(false)
        clusterManager.cluster()
//        setCameraView()

    }


    fun showAlertDialog(marker: Marker) {


        val builder = AlertDialog.Builder(this)
            .setPositiveButton("Yes") { dialog, id ->
                previousPinPoint?.remove()

                selectedMarker?.isVisible = true
                selectedMarker = marker
                calculateDirection(marker)

            }.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()

            }.setMessage("Do you want to go to this location?")
            .setTitle("Direction")
        builder.create().show()


    }

    fun showPolylineToMap(directionsResult: DirectionsResult) {
        lifecycleScope.launch {
            //Clear previous polyline
            withContext(Dispatchers.Main) {
                if (mPolyData.size > 0) {
                    for (data in mPolyData) {
                        data.polyline.remove()
                    }
                    mPolyData.clear()
                }

            }


            for ((i, route) in (directionsResult.routes).withIndex()) {
                val decodedPath = PolylineEncoding.decode(route.overviewPolyline.encodedPath)
                val newDecodedPath = ArrayList<LatLng>()
                for (latLng in decodedPath) {
                    newDecodedPath.add(LatLng(latLng.lat, latLng.lng))
                }


                withContext(Dispatchers.Main) {
                    val polyline = mMap.addPolyline(PolylineOptions().addAll(newDecodedPath))
                    val polyLineData = PolyLineData(polyline, route.legs[0])
                    mPolyData.add(polyLineData)
                    if (i == 0) {
                        mPolyline = polyline
                        polyline.color = ContextCompat.getColor(this@MainActivity, R.color.mBlue)
                        polyline.zIndex = 1F
                        polyline.isClickable = true
                        selectedMarker!!.isVisible = false

                        goToRoute(polyLineData)

                    } else {
                        polyline.color = ContextCompat.getColor(this@MainActivity, R.color.darkGrey)
                        polyline.zIndex = 0F
                        polyline.isClickable = true
                    }

                }

            }

        }


    }

    fun goToRoute(polyLineData: PolyLineData) {
        previousPinPoint?.remove()


        selectedMarker!!.isVisible = false
        previousPinPoint = mMap.addMarker(
            MarkerOptions().position(selectedMarker!!.position)
                .snippet(polyLineData!!.leg.duration.toString())
                .title(selectedMarker!!.title)
        )

    }


    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraMove() {

    }

    fun setupBottomSheet(view:View){

        BottomSheetBehavior.from(view).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }


}