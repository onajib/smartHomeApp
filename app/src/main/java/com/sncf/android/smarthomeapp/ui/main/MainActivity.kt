package com.sncf.android.smarthomeapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sncf.android.smarthomeapp.databinding.ActivityMainBinding
import com.sncf.android.smarthomeapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initViewModelObservations()
    }

    private fun initViewModelObservations() {
        mainViewModel.checkDbData().observe(this) {
            if (it == null) {
                mainViewModel.getData()
            }
        }

        mainViewModel.data.observe(this) {
            when (it) {
                is Resource.Success ->
                    it.data?.let { data ->
                        mainViewModel.insertDevices(data.devices)
                        mainViewModel.insertUser(data.user)
                    }
                is Resource.Error ->
                    it.message?.let { message ->
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        Timber.e(this.javaClass.simpleName, "Error: $message")
                    }
                else -> Toast.makeText(applicationContext, "Loading data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}