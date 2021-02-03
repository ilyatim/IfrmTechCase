package com.example.ifrmtechcase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import java.util.*

class Adapter(
    private val inflater: LayoutInflater,
    private val diffUtil: AdapterDiffUtil<Contact>
) : ListAdapter<Contact, ViewHolder>(diffUtil)/*, Filterable*/ {

    private var listAll: List<Contact> = mutableListOf()

    override fun submitList(list: List<Contact>?) {
        list?.let {
            if (listAll.isEmpty()) {
                listAll = list
            }
        }
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /*override fun getFilter(): Filter = filter

    private val filter: Filter = object : Filter() {

        var lastSize: Int = 0

        override fun performFiltering(charSeq: CharSequence?): FilterResults {

            val filteredList: MutableList<Contact> = mutableListOf()

            charSeq?.let {
                when {
                    it.isEmpty() -> {
                        filteredList.addAll(listAll)
                        lastSize = 0
                    }
                    it.length < lastSize -> {
                        lastSize = charSeq.length
                        filteredList.addAll(getFilledList(
                            listAll,
                            charSeq
                        ))
                    }
                    else -> {
                        lastSize = charSeq.length
                        filteredList.addAll(getFilledList(
                            currentList,
                            charSeq
                        ))
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

        private fun getFilledList(
            list: List<Contact>,
            charSeq: CharSequence
        ): MutableList<Contact> {
            val filteredList: MutableList<Contact> = mutableListOf()
            for (item in list) {
                if (contains(item.name, charSeq.toString())) {
                    filteredList.add(item)
                }
            }
            return filteredList
        }

        private fun contains(first: String, second: String): Boolean {
            return first.toLowerCase(Locale.ROOT).contains(second.toLowerCase(Locale.ROOT))
        }
    }*/
}