package com.danhdueexoictif.statemachine.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
    val status: Int? = null
) : Parcelable