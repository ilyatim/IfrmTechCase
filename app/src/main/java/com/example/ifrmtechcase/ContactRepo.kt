package com.example.ifrmtechcase

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log

class ContactRepo(private val context: Context) {

    private val contacts: MutableList<Contact> = mutableListOf()

    fun getContacts(): List<Contact> {

        clearContacts()

        val cursor: Cursor? = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )

        cursor?.use {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                    val number =
                        cursor.getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                    val photoUri =
                        cursor.getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                        )
                    contacts.add(Contact(
                        name,
                        number,
                        photoUri
                    ))
                }
            }
        }
        return contacts
    }

    private fun clearContacts() {
        contacts.clear()
    }
}