package com.sncf.android.smarthomeapp.repository

import com.sncf.android.smarthomeapp.data.local.DataDao
import com.sncf.android.smarthomeapp.data.model.DataResponse
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.data.model.User
import com.sncf.android.smarthomeapp.data.remote.IModuloTechApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val moduloTechApi: IModuloTechApi,
    private val dataDa: DataDao
) {

    suspend fun getData(): Response<DataResponse> = moduloTechApi.getDevices()

    fun getDevices() = dataDa.getDevices()

    suspend fun insertDevices(devices: List<Device>) = dataDa.insertDevices(devices)

    suspend fun insertDevice(device: Device) = dataDa.insertDevice(device)

    suspend fun updateDevice(device: Device) = dataDa.updateDevice(device)

    suspend fun deleteDevice(device: Device) = dataDa.deleteDevice(device)

    fun getUser() = dataDa.getUser()

    suspend fun insertUser(user: User) = dataDa.insertUser(user)

    suspend fun updateUser(user: User) = dataDa.updateUser(user)
}