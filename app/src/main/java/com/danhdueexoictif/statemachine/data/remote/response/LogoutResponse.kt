package com.danhdueexoictif.statemachine.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogoutResponse(
    val message: String? = null
) : Parcelable
