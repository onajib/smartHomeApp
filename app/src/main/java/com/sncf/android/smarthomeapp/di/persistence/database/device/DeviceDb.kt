package com.sncf.android.smarthomeapp.di.persistence.database.device

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sncf.android.smarthomeapp.ui.model.Device
import javax.inject.Singleton

@Singleton
@Database(entities = [(Device::class)], version = 1, exportSchema = false)
abstract class DeviceDb : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}