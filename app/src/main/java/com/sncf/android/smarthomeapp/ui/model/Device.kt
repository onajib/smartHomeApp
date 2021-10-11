package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "devices")
@Parcelize
open class Device(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var deviceName: String,
    var productType: String
) : Parcelable {
    override fun toString() = productType
}