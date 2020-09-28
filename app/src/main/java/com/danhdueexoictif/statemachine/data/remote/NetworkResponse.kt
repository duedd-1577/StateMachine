package com.danhdueexoictif.statemachine.data.remote

import com.danhdueexoictif.statemachine.data.remote.response.BaseResponse
import java.io.IOException
import lombok.Builder
import lombok.Data
import okhttp3.Headers

@Data
@Builder
sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * A request that resulted in a response with a 2xx status code that has a body.
     */
    @Data
    @Builder
    data class Success<T : Any>(val body: T, val headers: Headers? = null) :
        NetworkResponse<T, Nothing>()

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    @Data
    @Builder
    data class ServerError<U : Any>(val body: U?, val code: Int, val headers: Headers? = null) :
        NetworkResponse<Nothing, U>()

    /**
     * A request that resulted in a response with a 2xx status code that has a ERROR body.
     */
    @Data
    @Builder
    data class ServerBusinessError(val baseResponse: BaseResponse) :
        NetworkResponse<Nothing, Nothing>()

    /**
     * A request that didn't result in a response.
     */
    @Data
    @Builder
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()
}
