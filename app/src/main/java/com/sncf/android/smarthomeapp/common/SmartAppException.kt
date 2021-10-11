package com.sncf.android.smarthomeapp.common

class SmartAppException(
    var errorCode: Int? = null,
    var errorMessage: String? = null,
    var friendlyErrorMessageId: Int
) : Exception()