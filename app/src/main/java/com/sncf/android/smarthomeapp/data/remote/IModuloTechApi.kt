package com.sncf.android.smarthomeapp.data.remote

import com.sncf.android.smarthomeapp.data.model.DataResponse
import com.sncf.android.smarthomeapp.utils.Constants.BASE_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//////////////////////////////////////////////
// PARAMETERS
//////////////////////////////////////////////
private const val CONTENT_TYPE = "Content-Type: application/json;charset=UTF-8"

//////////////////////////////////////////////
// PATH
//////////////////////////////////////////////

private const val DEVICES_PATH = "/modulotest/data.json"

interface IModuloTechApi {

    @Headers(CONTENT_TYPE)
    @GET(BASE_URL + DEVICES_PATH)
    suspend fun getDevices(): Response<DataResponse>
}