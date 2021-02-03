package com.example.ifrmtechcase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class Adapter(
    private val inflater: LayoutInflater,
    private val diffUtil: AdapterDiffUtil<Contact>
) : ListAdapter<Contact, ViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}