package com.example.bunavigator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.example.bunavigator.R
import com.example.bunavigator.databinding.ItemDestinationBinding
import com.example.bunavigator.models.Destination

class SearchLocationListAdapter(context:Context,val destinationList: ArrayList<Destination>):
    ArrayAdapter<Destination>(context,0,destinationList){

    val itemsAll:List<Destination> = destinationList.clone() as ArrayList<Destination>
    var suggestions=ArrayList<Destination>()
    private var filter: Filter=object :Filter(){

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if(constraint!=null){
                suggestions.clear()
                for(destination in itemsAll){
                    if(destination.name.lowercase().contains(constraint.toString().lowercase())){
                        suggestions.add(destination)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count= suggestions.size
                filterResults
            }
            else{
                FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filteredList= results?.values as ArrayList<Destination>?
            if(results!=null && results.count>0){
                clear()
                for(c: Destination in filteredList ?: listOf<Destination>()){
                    add(c)
                }
                notifyDataSetChanged()
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as Destination).name
        }



    }



    override fun getFilter(): Filter {
        return filter
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mView=convertView
        if(mView==null){

            mView=ItemDestinationBinding.inflate(LayoutInflater.from(context)
                ,parent,false).root

        }

        val item=getItem(position)
        if(item!=null){
            mView?.findViewById<TextView>(R.id.tv_destination)
                ?.apply {
                    text=item.name
                }
            mView?.findViewById<ImageView>(R.id.imv_destination_goto)
                ?.apply {
                    visibility=View.GONE
                }

        }

        return mView
    }


}


