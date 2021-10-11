package com.sncf.android.smarthomeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.data.model.User
import javax.inject.Inject

@Database(entities = [Device::class, User::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getDataDao(): DataDao

    class Callback @Inject constructor() : RoomDatabase.Callback()
}