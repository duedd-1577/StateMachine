package com.danhdueexoictif.statemachine.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This object contain business error info retrieved from 2xx HTTP response.
 */
@Parcelize
open class BaseResponse(
    var errorCode: String? = null,
    val errorDetails: List<ErrorDetail>? = null,
    var errorMessage: String? = null,
    val errorStatus: Int? = null
) : Parcelable
