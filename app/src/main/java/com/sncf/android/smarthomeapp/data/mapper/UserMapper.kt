package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.UserApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.User
import com.sncf.android.smarthomeapp.utils.DateUtils
import java.util.*

object UserMapper : IMapper<UserApi, User> {

    override fun toUi(apiObject: UserApi) = User(
        firstName = apiObject.firstName,
        lastName = apiObject.lastName,
        address = AddressMapper.toUi(apiObject.address),
        birthDate = DateUtils.parse(Date(apiObject.birthDate.toLong()))
    )
}