package com.example.ifrmtechcase

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter

class Adapter(
    private val inflater: LayoutInflater,
    private val diffUtil: AdapterDiffUtil<Contact>
) : ListAdapter<Contact, ViewHolder>(diffUtil), Filterable {

    private lateinit var listAll: List<Contact>

    /*override fun submitList(list: List<Contact>?) {
        list?.let { listAll = it }
        super.submitList(list)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(charSeq: CharSequence?): FilterResults {
            val filteredList: MutableList<Contact> = mutableListOf()

            charSeq?.let {
                if (it.isEmpty()) {
                    filteredList.addAll(currentList)
                } else {
                    for (movie in currentList) {
                        if (movie.name.toLowerCase().contains(charSeq.toString().toLowerCase())) {
                            filteredList.add(movie)
                        }
                    }
                }
            }

            val result: FilterResults = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(charSeq: CharSequence?, results: FilterResults?) {
            results?.let {
                val list: List<Contact> = results.values as List<Contact>
                submitList(list)
            }
        }

    }
}