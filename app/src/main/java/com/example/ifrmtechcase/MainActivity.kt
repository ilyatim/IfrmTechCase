package com.example.ifrmtechcase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifrmtechcase.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),  {

    private final val REQUEST_CODE = 1

    private val viewModel: ViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = Adapter(
            LayoutInflater.from(this),
            AdapterDiffUtil()
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        checkContactPermission()

        viewModel.contacts.observe(this, Observer {
            adapter.submitList(it)
        })

        setContentView(binding.root)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> viewModel.initContacts()
        }
    }

    private fun checkContactPermission() {
        if (!hasPhoneContactsPermission(Manifest.permission.READ_CONTACTS)) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_CONTACTS
                ), REQUEST_CODE
            )
        }
        else {
            viewModel.initContacts()
        }
    }
    private fun hasPhoneContactsPermission(permission: String): Boolean {
        val hasPermission: Int = ContextCompat.checkSelfPermission(
            applicationContext,
            permission
        )
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }
}