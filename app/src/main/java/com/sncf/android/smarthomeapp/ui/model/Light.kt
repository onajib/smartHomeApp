package com.sncf.android.smarthomeapp.ui.model

class Light(
    id: Int,
    deviceName: String,
    productType: String,
    var intensity: Int?,
    var mode: String?
) : Device(id, deviceName, productType)