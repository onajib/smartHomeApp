package com.sncf.android.smarthomeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var city: String,
    var postalCode: Int,
    var street: String,
    var streetCode: String,
    var country: String
) : Parcelable