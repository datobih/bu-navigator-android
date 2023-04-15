package com.example.bunavigator

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.bunavigator.models.Destination
import com.example.bunavigator.models.OnboardingItem
import com.example.bunavigator.presentation.MainViewModel
import com.example.bunavigator.repository.MainRepository
import java.net.URL

object Constants {

    val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
    val SHARED_PREFERENCES_FIRST_TIME = "SHARED_PREFERENCES_FIRST_TIME"
    val SHARED_PREFERENCES_LOCATIONS = "SHARED_PREFERENCES_FIRST_LOCATIONS"

    val SAVED_HANDLE_DESTINATION_SAVED="SAVED_HANDLE_DESTINATION_SAVED"
    var sharedPreferences: SharedPreferences? = null
    var mainRepository: MainRepository? = null

    var mainViewModel:MainViewModel? = null
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



    var buDestinations = listOf<Destination>(
        Destination(
            "Geoscience Research Centre",
            6.8907522,
            3.7217974,
            imageUrl = "https://i.postimg.cc/x18ZqZLD/geo.jpg"
        , markerRes = R.drawable.no_1
        ),
        Destination(
            "Welch Hall",
            6.8912372,
            3.7218916,
            imageUrl = "https://i.postimg.cc/JncXxpDw/IMG-20230213-145930.jpg"
        ,markerRes = R.drawable.no_2
        ),
        Destination(
            "Laz Oti Library",
            6.8922464,
            3.7224947,
            imageUrl = "https://i.postimg.cc/qRLtygS4/IMG-20230213-150541.jpg"
            ,markerRes = R.drawable.no_3
        ),
        Destination(
            "Bucodel",
            6.8917208,
            3.7231847,
            imageUrl = "https://i.postimg.cc/QxNzpP93/IMG-20230213-150721.jpg"
            ,markerRes = R.drawable.no_4
        ),
        Destination(
            "Ameyo Adadevoh",
            6.8948227,
            3.7247719,
            imageUrl = "https://i.postimg.cc/rwwK28DP/IMG-20230214-162114.jpg"
            ,markerRes = R.drawable.no_5
        ),
        Destination(
            "Felicia Adebisi Dada",
            6.8935365,
            3.7251565,
            imageUrl = "https://i.postimg.cc/HW3mDbbT/IMG-20230214-162628.jpg"
            ,markerRes = R.drawable.no_6
        ),
        Destination(
            "Queen Esther Hall",
            6.8930532,
            3.7247585,
            imageUrl = "https://i.postimg.cc/yYQ790NL/IMG-20230214-162907.jpg"
            ,markerRes = R.drawable.no_7
        ),
        Destination(
            "Ogden Hall",
            6.8933941,
            3.7263316,
            imageUrl = "https://i.postimg.cc/DzfDcMYs/IMG-20230214-163328.jpg"
            ,markerRes = R.drawable.no_8
        ),
        Destination(
            "White Hall",
            6.8936756,
            3.7263799,
            imageUrl = "https://i.postimg.cc/QdC1kgJC/IMG-20230214-163534.jpg"
            ,markerRes = R.drawable.no_9
        ),
        Destination(
            "Babcock Stadium",
            6.8943240,
            3.7269361,
            imageUrl = "https://i.postimg.cc/SK0PBKs6/IMG-20230214-164615.jpg"
            ,markerRes = R.drawable.no_10
        ),
        Destination(
            "Babcock Fitness Center",
            6.8944838,
            3.7269837,
            imageUrl = "https://i.postimg.cc/HLrTWn4V/IMG-20230214-164821.jpg"
        ),
        Destination(
            "Havillah Gold Hall",
            6.8946562,
            3.7259099,
            imageUrl = "https://i.postimg.cc/CxSj2BpT/IMG-20230214-164020.jpg"
            ,markerRes = R.drawable.no_11
        ),
        Destination(
            "Crystal Hall",
            6.8928974,
            3.7277378,
            imageUrl = "https://i.postimg.cc/WzcVnPF8/IMG-20230214-165213.jpg"
            ,markerRes = R.drawable.no_12
        ),
        Destination(
            "Platinum Hall",
            6.8921518,
            3.7272268,
            imageUrl = "https://i.postimg.cc/mk5wsdbc/IMG-20230214-165401.jpg"
            ,markerRes = R.drawable.no_13
        ),
        Destination(
            "Diamond Hall",
            6.8917484,
            3.7269754,
            imageUrl = "https://i.postimg.cc/sD9W5jQn/IMG-20230214-165659.jpg"
            ,markerRes = R.drawable.no_14
        ),


        Destination(
            "Nyberg Hall",
            6.8925446,
            3.7253865,
            imageUrl = "https://i.postimg.cc/qMMJZ4DD/IMG-20230214-170016.jpg"
            ,markerRes = R.drawable.no_15
        ),
        Destination(
            "Amphitheatre",
            6.8908184,
            3.7224099,
            imageUrl = "https://i.postimg.cc/Zn5GjKYr/IMG-20230215-141428.jpg"
            ,markerRes = R.drawable.no_16
        ),
        Destination(
            "Chiramoke National Political Resource Centre",
            6.8905748,
            3.7229916,
            imageUrl = "https://i.postimg.cc/yNJXhqzk/IMG-20230215-141752.jpg"
            ,markerRes = R.drawable.no_17
        ),
        Destination(
            "New Horizon",
            6.8902759,
            3.7231616,
            imageUrl = "https://i.postimg.cc/HxD5QV2h/IMG-20230215-141727.jpg"
            ,markerRes = R.drawable.no_18
        ),
        Destination(
            "Bursary Division",
            6.8902759,
            3.7227073,
            imageUrl = "https://i.postimg.cc/1XWfYtGP/IMG-20230215-142120.jpg"
            ,markerRes = R.drawable.no_19
        ),
        Destination(
            "School of Education and Humanities",
            6.8902050,
            3.7208797,
            imageUrl = "https://i.postimg.cc/Y0H3q4g7/IMG-20230213-144759.jpg"
            ,markerRes = R.drawable.no_20
        ),
        Destination(
            "Babrite",
            6.8913380,
            3.7205337,
            imageUrl = "https://i.postimg.cc/jjQqF2T9/IMG-20230215-143205.jpg"
            ,markerRes = R.drawable.no_21
        ),
        Destination(
            "Topaz Hall",
            6.8934197,
            3.7203446,
            imageUrl = "https://i.postimg.cc/vmQwDzRq/IMG-20230215-143741.jpg"
            ,markerRes = R.drawable.no_22
        ),
        Destination(
            "Adeleke Hall",
            6.8929623,
            3.7209726,
            imageUrl = "https://i.postimg.cc/nV7xRM11/IMG-20230215-144032.jpg"
            ,markerRes = R.drawable.no_23
        ),
        Destination(
            "Neal Wilson",
            6.8931294,
            3.7218362,
            imageUrl = "https://i.postimg.cc/44r6G2Vg/IMG-20230215-144317.jpg"
            ,markerRes = R.drawable.no_24
        ),
        Destination(
            "Winslow Hall",
            6.8939566,
            3.7215221,
            imageUrl = "https://i.postimg.cc/nhwc8g9K/IMG-20230215-144533.jpg"
            ,markerRes = R.drawable.no_25
        ),
        Destination(
            "Nelson Mandela Hall",
            6.8935458,
            3.7228759,
            imageUrl = "https://i.postimg.cc/NfM3hM6V/IMG-20230215-144811.jpg"
            ,markerRes = R.drawable.no_26
        ),
        Destination(
            "Samuel Akande Hall",
            6.8942245,
            3.7235596,
            imageUrl = "https://i.postimg.cc/MKM9bjbW/IMG-20230215-145022.jpg"
            ,markerRes = R.drawable.no_27
        ), Destination(
            "Bethel Hall",
            6.8942245,
            3.7235596,
            imageUrl = "https://i.postimg.cc/3wKj6yp0/IMG-20230215-145117.jpg"
            ,markerRes = R.drawable.no_28
        ),
        Destination(
            "Gideon Troopers Hall",
            6.8945051,
            3.7223713,
            imageUrl = "https://i.postimg.cc/fRsgzRmD/IMG-20230215-145231.jpg"
            ,markerRes = R.drawable.no_29
        ),
        Destination(
            "Cafeteria",
            6.8926105,
            3.7236927,
            imageUrl = "https://i.postimg.cc/SxZLBSGZ/IMG-20230216-103204.jpg"
            ,markerRes = R.drawable.no_30
        ), Destination(
            "Registry",
            6.8926105,
            3.7219063,
            imageUrl = "https://i.postimg.cc/d1chXpgH/IMG-20230216-115831.jpg"
            ,markerRes = R.drawable.no_31
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
            "  {\n" +
            "    \"featureType\": \"administrative.land_parcel\",\n" +
            "    \"stylers\": [\n" +
            "      {\n" +
            "        \"visibility\": \"off\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"featureType\": \"administrative.neighborhood\",\n" +
            "    \"stylers\": [\n" +
            "      {\n" +
            "        \"visibility\": \"off\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
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