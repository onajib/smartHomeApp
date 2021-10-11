package com.sncf.android.smarthomeapp.data.persistence.device

import com.sncf.android.smarthomeapp.ui.model.Device

interface DeviceRepo {
    suspend fun insertAll(devices: List<Device>)
}