package com.sncf.android.smarthomeapp.utils

import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.domain.model.SmartAppException
import com.sncf.android.smarthomeapp.domain.network.StatusCode
import timber.log.Timber

object ErrorUtils {

    fun handleError(
        tag: String,
        errorMessage: String?,
        errorCode: Int? = null
    ): Exception {
        SmartAppException(
            errorCode = errorCode,
            errorMessage = errorMessage,
            friendlyErrorMessageId = evaluateMessageError(errorCode)
        )
            .let {
                Timber.tag(tag).e(it, errorMessage)
                return it
            }
    }

    fun evaluateErrorCode(throwable: Throwable?): Int =
        when (throwable) {
            is SmartAppException -> evaluateMessageError(throwable.errorCode)
            else -> StatusCode.Unknown.code
        }

    private fun evaluateMessageError(code: Int?): Int {
        return when (code) {
            StatusCode.Unauthorized.code, StatusCode.Forbidden.code -> R.string.unauthorized_error_message
            StatusCode.RequestTimeout.code -> R.string.time_out_error_message
            StatusCode.GatewayTimeout.code -> R.string.network_error_message
            else -> R.string.generic_error_message
        }
    }
}