package com.sncf.android.smarthomeapp.data.model

import java.math.BigInteger

data class UserApi(
    var firstName: String,
    var lastName: String,
    var address: AddressApi,
    var birthDate: BigInteger
)