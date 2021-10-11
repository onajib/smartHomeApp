package com.sncf.android.smarthomeapp.data.persistence.device

import com.sncf.android.smarthomeapp.di.persistence.database.device.DeviceDao
import com.sncf.android.smarthomeapp.ui.model.Device
import javax.inject.Inject

class DeviceRepoImpl @Inject constructor(
    private val deviceDao: DeviceDao
) : DeviceRepo {

    override suspend fun insertAll(devices: List<Device>) {
        deviceDao.createAll(devices)
    }
}