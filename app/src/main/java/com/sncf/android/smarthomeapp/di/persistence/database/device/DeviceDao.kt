package com.sncf.android.smarthomeapp.di.persistence.database.device

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.sncf.android.smarthomeapp.ui.model.Device

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @JvmSuppressWildcards
    fun createAll(devices: List<Device>)

    @Update
    fun updateDevice(vararg devices: Device)
}