package com.danhdueexoictif.statemachine.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorDetail(
    val message: String? = null,
    val properties: List<String>? = null
) : Parcelable
