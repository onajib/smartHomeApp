package com.sncf.android.smarthomeapp.data.model

data class AddressApi(
    var city: String,
    var postalCode: Int,
    var street: String,
    var streetCode: String,
    var country: String
)