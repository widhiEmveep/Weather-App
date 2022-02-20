package com.example.weatherapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.example.weatherapp.data.remote.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber


/**
 * Convert [ResponseBody.charStream] to [T] object
 */

inline fun <reified T : Any?> ResponseBody.parse(): T? {
    val classType = object : TypeToken<T>() {}.type
    return Gson().fromJson(charStream(), classType)
}

/**
 * Convert [Int] from error Body to Custom Error Message
 */
fun Int.handleCode() = when (this) {
    404 -> "Data not found"
    422 -> "Query you're entered is wrong"
    else -> "An error occurred. Please try again later"
}

// Exception Handler
private fun Throwable.handleExecption() = when (this) {
    is IOException -> "Failed to read response!"
    is SocketTimeoutException -> "Timeout!"
    is UnknownHostException -> "Please check your internet connection!"
    else -> "An error occurred!"
}

/**
 * Convert [Exception]/[Throwable] to Custom Error Message
 */
val Throwable.parsedMessage get() = handleExecption()

/**
 * @return [Flow] of [Result] object with 3 step of Result Handling
 * @param responseBody Used for [Response] callback value from ApiCallback
 */
inline fun <reified T> flowResponse(
    handleError: Boolean = true,
    crossinline responseBody: suspend () -> Response<T>
) = flow<Result<T>> {
    val response = responseBody.invoke()
    val body = response.body()

    if (response.isSuccessful) {
        val result = body
        emit(Result.success(result))
    } else {
        val isError = response.code() in 400..599
        val errorBody = response.errorBody()?.parse<T>()

        val message =
            if (isError && handleError) response.code().handleCode()
            else errorBody
    }
}
    .onStart { emit(Result.loading()) }
    .flowOn(Dispatchers.IO)
    .catch { throwable ->
        Timber.e("Error In ${T::class.java} : $throwable")
        emit(Result.error(throwable.parsedMessage, code = 500))
    }