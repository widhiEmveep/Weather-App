package com.example.weatherapp.data.remote

sealed class Result <T> {

    object Nothing : Result<kotlin.Nothing>() {
        override fun toString() = "Result.Nothing"
    }

    class Loading<T> : Result<T>() {
        override fun toString() = "Result.Loading"
    }

    class Success<T>(val data: T?) : Result<T>() {
        override fun toString() = "Result.Success with item : [$data]"
    }

    class Error<T>(
        val message: String,
        val data: T? = null,
        val status_code: Int = 0
    ) : Result<T>() {
        override fun toString() = "Result.Error with Item Of [Message: $data, Code: ${status_code}]"
    }

    companion object {
        val nothing = Nothing
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T?) = Success(data)
        fun <T> error(
            message: String,
            data: T? = null,
            code: Int = 0
        ) = Error<T>(message, data, code)
    }
}