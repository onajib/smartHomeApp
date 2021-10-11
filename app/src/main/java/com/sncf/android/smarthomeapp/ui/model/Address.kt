package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Address(
    var city: String,
    var postalCode: String,
    var street: String,
    var streetCode: String,
    var country: String
) : Parcelable