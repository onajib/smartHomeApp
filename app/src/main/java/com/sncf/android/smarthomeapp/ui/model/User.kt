package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var firstName: String,
    var lastName: String,
    var address: Address,
    var birthDate: String
) : Parcelable