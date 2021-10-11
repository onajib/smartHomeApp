package com.sncf.android.smarthomeapp.domain.model

class SmartAppException(
    var errorCode: Int? = null,
    var errorMessage: String? = null,
    var friendlyErrorMessageId: Int
) : Exception()