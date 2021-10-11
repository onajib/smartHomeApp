package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.AddressApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Address

object AddressMapper : IMapper<AddressApi, Address> {

    override fun toUi(apiObject: AddressApi) = Address(
        city = apiObject.city,
        postalCode = apiObject.postalCode.toString(),
        street = apiObject.street,
        streetCode = apiObject.streetCode,
        country = apiObject.country
    )
}