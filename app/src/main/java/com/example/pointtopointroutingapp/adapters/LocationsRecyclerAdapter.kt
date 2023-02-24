package com.example.pointtopointroutingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pointtopointroutingapp.databinding.ItemDestinationQueryBinding
import com.example.pointtopointroutingapp.models.Destination

class LocationsRecyclerAdapter :
    ListAdapter<Destination, LocationsRecyclerAdapter.InnerViewHolder>(DIFF_UTIl) {



    class InnerViewHolder(val binding: ItemDestinationQueryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(destination: Destination) {
            binding.tvLocationName.text=destination.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
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