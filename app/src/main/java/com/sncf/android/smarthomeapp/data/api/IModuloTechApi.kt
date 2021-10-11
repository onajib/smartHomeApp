package com.sncf.android.smarthomeapp.data.api

import com.sncf.android.smarthomeapp.data.model.DataResponseApi
import com.sncf.android.smarthomeapp.utils.Constants.BASE_URL
import io.reactivex.rxjava3.core.Single
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
    fun getDevices(
    ): Single<Response<DataResponseApi>>
}