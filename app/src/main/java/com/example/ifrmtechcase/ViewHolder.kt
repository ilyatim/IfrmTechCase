package com.example.ifrmtechcase

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.ifrmtechcase.databinding.CellBinding

class ViewHolder(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup,
    private val binding: CellBinding = CellBinding.inflate(
        layoutInflater,
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cell: Contact) {
        binding.contactName.text = cell.name
        binding.contactNumber.text = cell.number
        cell.photoUri?.let {
            val uri = Uri.parse(cell.photoUri)
            Log.i("SomeTag", "ViewHolder - $uri")
            Glide.with(binding.root)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.ic_round_person)
                .transform(CircleCrop())
                .into(binding.contactPhoto)
        }
    }

}