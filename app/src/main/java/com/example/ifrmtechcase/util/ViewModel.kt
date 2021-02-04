package com.example.ifrmtechcase.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ifrmtechcase.data.Contact
import com.example.ifrmtechcase.data.ContactRepo
import java.util.*

class ViewModel(app: Application) : AndroidViewModel(app) {

    private val contactRepo: ContactRepo = ContactRepo(app.baseContext)
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    private var allContacts: List<Contact> = listOf()

    val contacts: LiveData<List<Contact>> get() = _contacts

    fun initContacts() {
        _contacts.value = contactRepo.getContacts()
        _contacts.value?.let { allContacts = it }
    }

    /**
     * Filtering contacts
     * @param query - the substring to filter
     */
    fun filtering(query: String) = _contacts.postValue(allContacts.filter { contact ->
        if (query.isNotEmpty()) {
            contains(contact.name, query) || contains(contact.number, query)
        } else true
    })

    /**
     * Returns true if this char sequence contains the specified other sequence of characters as a substring
     * @param first - string
     * @param second - substring
     */
    private fun contains(first: String, second: String): Boolean {
        return first.toLowerCase(Locale.ROOT).contains(second.toLowerCase(Locale.ROOT))
    }
}