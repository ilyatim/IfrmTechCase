package com.example.ifrmtechcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.ifrmtechcase.util.AdapterDiffUtil
import com.example.ifrmtechcase.data.Contact

/**
 * Adapter for recycler view
 * @param inflater - Instantiates a layout XML file into its corresponding View objects
 * @param diffUtil - utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one
 */
class Adapter(
    private val inflater: LayoutInflater,
    private val diffUtil: AdapterDiffUtil<Contact>
) : ListAdapter<Contact, ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}