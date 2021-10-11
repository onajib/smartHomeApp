package com.sncf.android.smarthomeapp.ui.profil.form

import com.sncf.android.smarthomeapp.utils.Constants.DATE_REGEX
import com.sncf.android.smarthomeapp.utils.DateUtils.isValidDate
import com.sncf.android.smarthomeapp.utils.TextUtils.isEmpty
import com.sncf.android.smarthomeapp.utils.TextUtils.isIntParsable

class ProfileForm(
    var id: Int,
    var lastName: String?,
    var firstName: String?,
    var birthDate: String?,
    var street: String?,
    var streetCode: String?,
    var postalCode: String?,
    var city: String?,
    var country: String?
) {
    fun isValid(): Boolean =
        !isEmpty(firstName) &&
                !isEmpty(lastName) &&
                isValidDate(birthDate, DATE_REGEX) &&
                !isEmpty(street) &&
                !isEmpty(streetCode) &&
                isIntParsable(postalCode) &&
                !isEmpty(city) &&
                !isEmpty(country)
}