package com.example.ifrmtechcase

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

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