package com.example.bunavigator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bunavigator.databinding.ItemDestinationQueryBinding
import com.example.bunavigator.models.Destination

class LocationsRecyclerAdapter :
    ListAdapter<Destination, LocationsRecyclerAdapter.InnerViewHolder>(DIFF_UTIl) {

    var context:Context? = null

    inner class InnerViewHolder(val binding: ItemDestinationQueryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(destination: Destination) {
            Glide.with(context!!)
                .load(destination.imageUrl)
                .centerCrop()
                .into(binding.imvLocation)
            binding.tvLocationName.text=destination.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        if(context==null) context=parent.context
        return InnerViewHolder(ItemDestinationQueryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object{

        val DIFF_UTIl=object : DiffUtil.ItemCallback<Destination>() {
            override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {

                return oldItem.name==newItem.name
            }

            override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {

                return oldItem.equals(newItem)
            }

        }



    }


}