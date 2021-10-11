package com.sncf.android.smarthomeapp.domain.mapper

interface IMapper<A, U> {

    fun toUi(apiObject: A): U
}