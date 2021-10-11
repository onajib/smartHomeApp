package com.sncf.android.smarthomeapp.ui.main

import androidx.lifecycle.LiveData
import com.sncf.android.smarthomeapp.ui.model.Device
import com.sncf.android.smarthomeapp.ui.model.User

interface IMainContract {

    interface ViewModel {

        val errorMessage: LiveData<Int>

        val devices: LiveData<ArrayList<Device>>

        val user: LiveData<User>

        fun getData()
    }
}