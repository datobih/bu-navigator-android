package com.example.pointtopointroutingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.recyclerview.widget.RecyclerView
import com.example.pointtopointroutingapp.R
import com.example.pointtopointroutingapp.databinding.ItemOnboardingPageBinding
import com.example.pointtopointroutingapp.models.OnboardingItem

class OnboardingViewPagerAdapter(val pagesList:List<OnboardingItem>):RecyclerView.Adapter<OnboardingViewPagerAdapter.OnboardingViewHolder>() {

    class OnboardingViewHolder(val binding:ItemOnboardingPageBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding= ItemOnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context),parent,
            false
        )
        return OnboardingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pagesList.size
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
    if(holder is OnboardingViewHolder){
        with(holder.binding) {
           animationView.setAnimation(pagesList[position].image)
            tvTitle.text = pagesList[position].title
            tvOnboardingDescription.text=pagesList[position].description
        }





    }



    }

}