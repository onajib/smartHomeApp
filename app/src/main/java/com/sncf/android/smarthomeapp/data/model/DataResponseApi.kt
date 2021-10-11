package com.sncf.android.smarthomeapp.data.model

data class DataResponseApi(
    var devices: List<DeviceApi>,
    var user: UserApi
)