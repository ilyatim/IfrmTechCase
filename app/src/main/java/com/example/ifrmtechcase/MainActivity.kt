package com.example.ifrmtechcase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity() {

    private final val REQUEST_CODE = 1

    private val viewModel: ViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = Adapter(
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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter.filter(newText)
                binding.recyclerview.smoothScrollToPosition(0)
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {}
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
    }
    private fun hasPhoneContactsPermission(permission: String): Boolean {
        val hasPermission: Int = ContextCompat.checkSelfPermission(
            applicationContext,
            permission
        )
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }
}