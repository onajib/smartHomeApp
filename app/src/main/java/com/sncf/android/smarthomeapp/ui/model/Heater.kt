package com.sncf.android.smarthomeapp.ui.model

class Heater(
    id: Int,
    deviceName: String,
    productType: String,
    var mode: String?,
    var temperature: Int?
) : Device(id, deviceName, productType)