package com.sncf.android.smarthomeapp.ui.devices

import com.sncf.android.smarthomeapp.ui.model.Device

interface IDeviceListener {

    fun onDeviceClicked(device: Device)
}