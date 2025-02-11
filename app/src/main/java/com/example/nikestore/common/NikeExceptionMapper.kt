package com.example.nikestore.common

import com.example.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object {
        fun map(throwable: Throwable): NikeException {
            if (throwable is HttpException) {
                try {
                    val jsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val message = jsonObject.getString("message")
                    return NikeException(
                        if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,
                        serverMessage = message
                    )
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }
            return NikeException(NikeException.Type.SIMPLE, R.string.unknownError)
        }
    }
}