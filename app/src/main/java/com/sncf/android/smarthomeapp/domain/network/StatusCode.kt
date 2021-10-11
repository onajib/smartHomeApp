package com.sncf.android.smarthomeapp.domain.network

private const val CODE_401 = 401
private const val CODE_403 = 403
private const val CODE_408 = 408
private const val CODE_504 = 504
private const val CODE_520 = 520

enum class StatusCode(val code: Int) {
    Unauthorized(CODE_401),
    Forbidden(CODE_403),
    RequestTimeout(CODE_408),
    GatewayTimeout(CODE_504),
    Unknown(CODE_520)
}