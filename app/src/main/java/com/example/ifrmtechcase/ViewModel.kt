package com.example.ifrmtechcase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel(app: Application) : AndroidViewModel(app) {

    private val contactRepo: ContactRepo = ContactRepo(app.baseContext)
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()

    val contacts: LiveData<List<Contact>> get() = _contacts

    fun initContacts() {
        _contacts.value = contactRepo.getContacts()
    }
}