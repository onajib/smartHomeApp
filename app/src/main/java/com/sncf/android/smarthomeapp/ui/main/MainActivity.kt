package com.sncf.android.smarthomeapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.viewModels
import com.sncf.android.smarthomeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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

        mainViewModel.getData()
    }

    private fun initViewModelObservations() {
        mainViewModel.errorMessage.observe(this, {
            Toast.makeText(
                this,
                resources.getString(it),
                Toast.LENGTH_LONG
            ).show()
        })
    }
}