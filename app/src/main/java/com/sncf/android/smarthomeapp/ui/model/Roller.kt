package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Roller(
    var id: Int,
    var deviceName: String,
    var productType: String,
    var position: Int?,
) : Parcelable