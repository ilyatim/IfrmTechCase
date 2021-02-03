package com.example.ifrmtechcase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifrmtechcase.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class MainActivity : AppCompatActivity() {

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

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.recyclerview.layoutManager as LinearLayoutManager)
                        .scrollToPosition(0)
            }
        })
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
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.filtering(newText)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
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