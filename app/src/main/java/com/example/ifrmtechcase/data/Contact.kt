package com.example.ifrmtechcase.data

/**
 * Recycler view cell data class
 * @param name - name from contact
 * @param number - phone number from contact
 * @param photoUri - contact photo
 */
data class Contact(
    val name: String,
    val number: String,
    val photoUri: String?,
)