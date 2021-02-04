package com.example.ifrmtechcase.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifrmtechcase.util.AdapterDiffUtil
import com.example.ifrmtechcase.R
import com.example.ifrmtechcase.util.ViewModel
import com.example.ifrmtechcase.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1
    private val recyclerPositionAfterSearch = 0

    private val viewModel: ViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            (binding.recyclerview.layoutManager as LinearLayoutManager)
                    .scrollToPosition(recyclerPositionAfterSearch)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = Adapter(LayoutInflater.from(this), AdapterDiffUtil())
        adapter.registerAdapterDataObserver(adapterDataObserver)

        if (savedInstanceState == null) {
            checkContactPermission()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.recyclerview.adapter = adapter

        searchViewListenerHandler(
                binding.toolbar
                        .menu
                        .findItem(R.id.action_search)
                        .actionView as SearchView
        )

        viewModel.contacts.observe(this, Observer {
            adapter.submitList(it)
        })

        setContentView(binding.root)
    }

    private fun searchViewListenerHandler(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.filtering(newText)
                }
                return false
            }
        })
    }

    private fun checkContactPermission() {
        if (!hasPhoneContactsPermission(Manifest.permission.READ_CONTACTS)) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
        } else {
            viewModel.initContacts()
        }
    }

    private fun showNoContactPermissionSnackBar() {
        Snackbar.make(
                binding.root,
                "Contact permission isn't granted",
                Snackbar.LENGTH_LONG
        ).setAction("SETTINGS") {
                openApplicationSettings()
                Toast.makeText(
                        applicationContext,
                        "Open Permissions and grant the contact permission",
                        Toast.LENGTH_LONG
                ).show()
            }.show()
    }

    /**
     * Open settings for result
     */
    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
        )
        startActivityForResult(appSettingsIntent, REQUEST_CODE)
    }

    /**
     * Check that user gave contact permission
     * @param permission - name of permission
     */
    private fun hasPhoneContactsPermission(permission: String): Boolean {
        val hasPermission: Int = ContextCompat.checkSelfPermission(
                applicationContext,
                permission
        )
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Return result of requestPermissions()
     * @param requestCode - The integer request code originally supplied to requestPermissions()
     * @param permissions
     * @param grantResults - the result of requested permission
     */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.initContacts()
                } else {
                    showNoContactPermissionSnackBar()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Return result after user open settings
     * @param requestCode - The integer request code originally supplied to startActivityForResult(),
     * allowing you to identify who this result came from
     * @param resultCode - The integer result code returned by the child activity through its setResult().
     * @param data - An Intent, which can return result data to the caller.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            checkContactPermission();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}