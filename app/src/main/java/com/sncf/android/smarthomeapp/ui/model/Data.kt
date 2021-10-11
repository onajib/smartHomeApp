package com.sncf.android.smarthomeapp.ui.model

data class Data(
    var lightDevices: List<Light>,
    var heaterDevices: List<Heater>,
    var rollerDevices: List<Roller>,
    var user: User
)