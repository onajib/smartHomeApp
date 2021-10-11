package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.DeviceApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Roller

object RollerMapper : IMapper<DeviceApi, Roller> {

    override fun toUi(apiObject: DeviceApi) = Roller(
        id = apiObject.id,
        deviceName = apiObject.deviceName,
        productType = apiObject.productType,
        position = apiObject.position
    )
}