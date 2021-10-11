package com.sncf.android.smarthomeapp.utils

import android.widget.TextView
import com.sncf.android.smarthomeapp.utils.Constants.INT_REGEX

object TextUtils {

    private const val errorMessage = "Please fill in the field or check it's format"

    fun isEmpty(text: String?) = text.isNullOrEmpty()

    fun isIntParsable(text: String?) = text?.matches(INT_REGEX) ?: false

    fun highlightMandatoryField(etField: TextView?, isOnError: Boolean) {
        if (isOnError) {
            etField?.error = errorMessage
        }
    }
}