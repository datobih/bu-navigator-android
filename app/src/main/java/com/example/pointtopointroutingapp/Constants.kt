package com.example.pointtopointroutingapp

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.pointtopointroutingapp.models.Destination
import com.example.pointtopointroutingapp.models.OnboardingItem
import com.example.pointtopointroutingapp.repository.MainRepository
import java.net.URL

object Constants {

    val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
    val SHARED_PREFERENCES_FIRST_TIME = "SHARED_PREFERENCES_FIRST_TIME"
    val SHARED_PREFERENCES_LOCATIONS = "SHARED_PREFERENCES_FIRST_LOCATIONS"
    var sharedPreferences: SharedPreferences? = null
    var mainRepository: MainRepository? = null


    val onBoardingItems = listOf<OnboardingItem>(
        OnboardingItem(
            "Welcome to the future of campus navigation!", R.raw.map_navigation,
            "Say goodbye to the days of getting lost on campus and hello to a new era of seamless navigation." +
                    " Our cutting-edge point-to-point routing system is designed to help you easily find your way around campus."
        ),
        OnboardingItem(
            "Discover new paths and reach your destination with ease", R.raw.dlivery_map,
            "Say goodbye to monotonous and congested routes, and hello to an ever-evolving world of campus navigation. Get ready to discover new paths and reach your destination with ease, today!"
        ),
        OnboardingItem(
            "Let's get you started on your journey", R.raw.get_started,
            "Our goal is to make your campus navigation experience as smooth and effortless as possible. In just a few simple steps, we'll get you set up and ready to explore your surroundings like never before. "
        )
    )

    val destinations = listOf<Destination>(
        Destination(
            "Cardiac Center", 41.26636, -95.95708,
            R.drawable.no_1
        ),
        Destination(
            "Boyne Building", 41.26643, -95.95465,
            R.drawable.no_2
        ),
        Destination(
            "Bio-Information Center", 41.26632, -95.95371,
            R.drawable.no_3
        ),
        Destination(
            "Criss Complex II-III", 41.266373, -95.950909,
            R.drawable.no_4
        ),
        Destination(
            "Hixson-Lied Science Building", 41.26623, -95.95027,
            R.drawable.no_5
        ),
        Destination(
            "Eppley Building", 41.26582, -95.94985,
            R.drawable.no_6
        ),
        Destination(
            "Rigge Science Building", 41.26586, -95.95054,
            R.drawable.no_7
        ),
        Destination(
            "Criss I", 41.26580, -95.95109,
            R.drawable.no_8
        ),
        Destination(
            "Beirne Research Tower", 41.26544, -95.95110,
            R.drawable.no_9
        ),
        Destination(
            "Dowling Hall(Humanities)", 41.26515, -95.95057,
            R.drawable.no_10
        ),
        Destination(
            "Hitchcock Building(Communication Arts)", 41.26513, -95.94993,
            R.drawable.no_11, "DR. Baker HCCA 203D"
        ),

        Destination(
            "Becker Dining Hall", 41.26423, -95.95081,
            R.drawable.no_12
        ),
        Destination(
            "Kiewit Residence Hall", 41.26433, -95.95042,
            R.drawable.no_13
        ),
        Destination(
            "Skutt Student Center", 41.26440, -95.94960,
            R.drawable.no_14
        ),
        Destination(
            "Kiewit Fitness Center", 41.26398, -95.94897,
            R.drawable.no_15,
        ),
        Destination(
            "Swanson Residence Hall", 41.26415, -95.94790,
            R.drawable.no_16
        ),
        Destination(
            "Brandeis Dining Hall", 41.26417, -95.94764,
            R.drawable.no_17
        ),
        Destination(
            "Dengleman Hall", 41.26449, -95.94730,
            R.drawable.no_18
        ),
        Destination(
            "Creighton Hall(Administration Building)", 41.26509, -95.94771,
            R.drawable.no_19
        ),
        Destination(
            "St. John's Church", 41.26517, -95.94834,
            R.drawable.no_20
        ),
        Destination(
            "Reinert-Alumni Memorial Library", 41.26541, -95.94914,
            R.drawable.no_21
        ),

        Destination(
            "Stuppy Greenhouse", 41.26605, -95.94890,
            R.drawable.no_22
        ),
        Destination(
            "Markoe Hall", 41.26677, -95.94864,
            R.drawable.no_23
        ),
        Destination(
            "Vinardi Center(Old Gymnasium)", 41.26659, -95.94802,
            R.drawable.no_24
        ),
        Destination(
            "CHI Health Creighton University Medical Center", 41.26509, -95.95445,
            R.drawable.no_25
        ),

        Destination(
            "Schneider Hall", 41.26726, -95.94587,
            R.drawable.no_26
        ),
        Destination(
            "School of Dentistry", 41.267480, -95.944410,
            R.drawable.no_27
        ),


        Destination(
            "Sports Complex", 41.26597, -95.94489,
            R.drawable.no_28
        ),
        Destination(
            "Kitty Gaughan Pavilion", 41.265575, -95.945135,
            R.drawable.no_29
        ),

        Destination(
            "McGloin Residence Hall", 41.26510, -95.94565,
            R.drawable.no_30
        ),
        Destination(
            "Lied Education Center for the Arts", 41.26448, -95.94555,
            R.drawable.no_31
        ),
        Destination(
            "Ahmanson Building(School of Law)", 41.26443, -95.94438,
            R.drawable.no_32
        ),
        Destination(
            "Heider Hall", 41.26185, -95.94536,
            R.drawable.no_33
        ),
        Destination(
            "Kenefick Hall", 41.26250, -95.94272,
            R.drawable.no_34
        ),
        Destination(
            "Linn Building", 41.26398, -95.94217,
            R.drawable.no_35
        ),
        Destination(
            "Labaj Building", 41.26444, -95.94223,
            R.drawable.no_36
        ),

        Destination(
            "Opus Residence Hall", 41.26502, -95.94210,
            R.drawable.no_37
        ),
        Destination(
            "Harper Center and the Heider College of Business", 41.26498, -95.94355,
            R.drawable.no_38
        ),
        Destination(
            "Davis Square(Residence Hall)", 41.26630, -95.94331,
            R.drawable.no_39
        ),
        Destination(
            "Pittman Building", 41.26714, -95.94266,
            R.drawable.no_40
        ),
        Destination(
            "Murphy Building", 41.26607, -95.94215,
            R.drawable.no_41
        ),
        Destination(
            "Boys Town National Research Hospital", 41.26417, -95.95593,
            R.drawable.no_42
        ),
        Destination(
            "Parle Building(Military Science/ROTC)", 41.26768, -95.95127,
            R.drawable.no_43
        ),
        Destination(
            "Ryan Athletic Center/D.J. Sokol Arena", 41.26598, -95.94085,
            R.drawable.no_44
        ),
        Destination(
            "Rasmussen Fitness and Sports Center", 41.26597, -95.93949,
            R.drawable.no_47
        ),

        Destination(
            "Center for Health Policy and Ethics", 41.26718, -95.95112,
            R.drawable.no_48
        ),
        Destination(
            "Wareham Building", 41.26604, -95.93819,
            R.drawable.no_49
        ),
        Destination(
            "Championship Center", 41.26562, -95.94082,
            R.drawable.no_45
        ),
        Destination(
            "Morrison Soccer Stadium", 41.26495, -95.94004,
            R.drawable.no_46
        ),
        Destination(
            "Criss Health Sciences Building", 41.26628, -95.95089,
            R.drawable.no_50
        ),
        Destination(
            "Gallagher Residence Hall", 41.26444, -95.95118,
            R.drawable.no_51
        ),
        Destination(
            "Jahn Building", 41.26722, -95.94532,
            R.drawable.no_52
        ),
        Destination(
            "Campion House", 41.26448, -95.94128,
            R.drawable.no_53
        ),
        Destination(
            "Jelinek Building", 41.26672, -95.93947,
            R.drawable.no_54
        ),

        Destination(
            "Ignatius House", 41.26606, -95.94825,
            R.drawable.no_55
        ),
        Destination(
            "Observatory", 41.26615, -95.94735,
            R.drawable.no_56
        ),
        Destination(
            "Gaughan Pavillion", 41.26583, -95.94526,
            R.drawable.no_57
        ),

        )

    val buDestinations = listOf<Destination>(
        Destination(
            "Geoscience Research Centre",
            6.8907522,
            3.7217974,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        ),
        Destination(
            "Welch",
            6.8912372,
            3.7218916,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        )
,
        Destination(
            "Laz Oti Library",
            6.8922464,
            3.7224947,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        ),
        Destination(
            "Bucodel",
            6.8917208,
            3.7231847,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        ),
        Destination(
            "Ameyo Adadevoh",
            6.8948227,
            3.7247719,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        )
    )




    val mapStyle = "[\n" +
            "  {\n" +
            "    \"elementType\": \"labels\",\n" +
            "    \"stylers\": [\n" +
            "      {\n" +
            "        \"visibility\": \"off\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +


            "]"

    fun downloadBitmap(url: String): Bitmap {
        var bitmap: Bitmap? = null
        val connection = URL(url).openConnection()
        connection.doInput = true
        val input = connection.getInputStream()
        bitmap = BitmapFactory.decodeStream(input)
        bitmap = Bitmap.createScaledBitmap(bitmap!!, 150, 150, false)
        input.close()


        return bitmap!!

    }
}