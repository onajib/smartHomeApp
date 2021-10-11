package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.DeviceApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Light

object LightMapper : IMapper<DeviceApi, Light> {

    override fun toUi(apiObject: DeviceApi) = Light(
        id = apiObject.id,
        deviceName = apiObject.deviceName,
        productType = apiObject.productType,
        intensity = apiObject.intensity,
        mode = apiObject.mode
    )
}