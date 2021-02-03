package com.example.ifrmtechcase

import android.app.Application
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ViewModel(app: Application) : AndroidViewModel(app) {

    private val contactRepo: ContactRepo = ContactRepo(app.baseContext)
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    private var allContacts: List<Contact> = listOf()

    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        _contacts.value = contactRepo.getContacts()
        _contacts.value?.let { allContacts = it }
    }

    fun filtering(query: String) = _contacts.postValue(allContacts.filter { contact ->
        if (query.isNotEmpty()) {
            contains(contact.name, query)
        } else true
    })

    private fun contains(first: String, second: String): Boolean {
        return first.toLowerCase(Locale.ROOT).contains(second.toLowerCase(Locale.ROOT))
    }
}