package com.example.ifrmtechcase

import android.app.Application
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ViewModel(app: Application) : AndroidViewModel(app), Filterable {

    private val contactRepo: ContactRepo = ContactRepo(app.baseContext)
    private var allContacts: List<Contact> = listOf()
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()

    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        _contacts.value = contactRepo.getContacts()
        _contacts.value?.let { allContacts = it }
    }

    /*fun pushToUi() = filteredContacts.postValue(contacts.value?.filter {

    })*/

    override fun getFilter(): Filter = filter

    private val filter: Filter = object : Filter() {

        var lastSize: Int = 0

        override fun performFiltering(charSeq: CharSequence?): FilterResults {

            val filteredList: MutableList<Contact> = mutableListOf()

            charSeq?.let {
                when {
                    it.isEmpty() -> {
                        filteredList.addAll(allContacts)
                        lastSize = 0
                    }
                    it.length < lastSize -> {
                        lastSize = charSeq.length
                        filteredList.addAll(getFilledList(
                            allContacts,
                            charSeq
                        ))
                    }
                    else -> {
                        lastSize = charSeq.length
                        filteredList.addAll(getFilledList(
                            _contacts.value ?: allContacts,
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
                _contacts.value = list
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
    }
}