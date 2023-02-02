package com.example.pointtopointroutingapp.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.pointtopointroutingapp.Constants
import com.example.pointtopointroutingapp.R
import com.example.pointtopointroutingapp.adapters.OnboardingViewPagerAdapter
import com.example.pointtopointroutingapp.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter= OnboardingViewPagerAdapter(Constants.onBoardingItems)
        binding.vpOnboarding.adapter=adapter



        binding.vpOnboarding.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
            dotIndicatorSelectPosition(position)


            }
        })
    }

    fun dotIndicatorSelectPosition(position:Int){
        val dotItems=arrayOf(binding.dotIndicator1,binding.dotIndicator2,binding.dotIndicator3)
        for(i in 0..2){

            if(i==position){
                dotItems[i].setCardBackgroundColor(getColor(R.color.button_purp))
            }
            else{
                dotItems[i].setCardBackgroundColor(Color.parseColor("#F6F6F6"))
            }

        }
        with(binding.btnGetStarted) {
            if(position==dotItems.lastIndex) visibility=View.VISIBLE
            else visibility=View.GONE
        }

    }

}