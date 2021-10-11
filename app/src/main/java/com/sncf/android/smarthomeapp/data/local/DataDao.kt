package com.sncf.android.smarthomeapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy

import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.data.model.User

@Dao
interface DataDao {

    @Query("SELECT * FROM device_table")
    fun getDevices(): LiveData<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevices(devices: List<Device>)

    @Insert
    suspend fun insertDevice(device: Device)

    @Delete
    suspend fun deleteDevice(device: Device)

    @Update
    suspend fun updateDevice(device: Device)

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}