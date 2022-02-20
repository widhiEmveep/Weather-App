package com.example.weatherapp.base

data class BaseResponse<T>(
    var data: T? = null,
    var status: Any? = null,
    var status_code: Int = 0,
    var message: String? = null,
    var total: Int? = 0,
    var error: Boolean = false
)