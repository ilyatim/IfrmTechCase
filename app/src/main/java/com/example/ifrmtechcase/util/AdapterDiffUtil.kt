package com.example.ifrmtechcase.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * utility class that calculates the difference between two lists and outputs a list of update
 * operations that converts the first list into the second one
 */
class AdapterDiffUtil<V>() : DiffUtil.ItemCallback<V>() {
    override fun areItemsTheSame(
        oldItem: V,
        newItem: V
    ): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: V,
        newItem: V
    ): Boolean {
        return oldItem == newItem
    }
}