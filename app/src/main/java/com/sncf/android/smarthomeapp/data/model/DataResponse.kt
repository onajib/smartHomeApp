package com.sncf.android.smarthomeapp.data.model

data class DataResponse(
    var devices: MutableList<Device>,
    var user: User
)