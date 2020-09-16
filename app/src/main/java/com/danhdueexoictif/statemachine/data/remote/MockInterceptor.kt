package com.danhdueexoictif.statemachine.data.remote

import android.content.res.AssetManager
import com.danhdueexoictif.statemachine.BuildConfig
import com.danhdueexoictif.statemachine.data.remote.HttpResponseCode.HTTP_OK
import lombok.Builder
import lombok.Data
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * This will help us to test our networking code while a particular API is not implemented
 * yet on Backend side.
 */
@Builder
@Data
class MockInterceptor(private val assets: AssetManager) : Interceptor {

    @lombok.Generated
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
//                uri.endsWith(LOGIN_ROUT) -> getJsonStringFromFile(
//                    assets,
//                    MOCK_LOGIN_SUCCESS_RES_JSON_FILE_NAME
//                )
                else -> ""
            }

            return if (responseString.isNotEmpty()) {
                chain.proceed(chain.request())
                    .newBuilder()
                    .code(HTTP_OK)
                    .protocol(Protocol.HTTP_2)
                    .message(responseString)
                    .body(
                        responseString.toByteArray()
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            } else {
                chain.proceed(chain.request())
            }
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    companion object {
        const val LOGIN_ROUT = "login"
        const val MOCK_LOGIN_SUCCESS_RES_JSON_FILE_NAME = "mock_login_success.json"
    }

}
