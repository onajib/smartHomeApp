package com.sncf.android.smarthomeapp.ui.model

class Roller(
    id: Int,
    deviceName: String,
    productType: String,
    var position: Int?,
) : Device(id, deviceName, productType)