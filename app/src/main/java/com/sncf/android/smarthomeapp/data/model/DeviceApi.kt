package com.sncf.android.smarthomeapp.data.model

data class DeviceApi(
    var id: Int,
    var deviceName: String,
    var intensity: Int?,
    var mode: String?,
    var position: Int?,
    var temperature: Int?,
    var productType: String
)