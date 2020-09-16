package com.danhdueexoictif.statemachine.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OauthToken(
    var tokenType: String? = null,
    var expiresIn: Long? = null,
    var refreshToken: String? = null,
    var accessToken: String? = null,
    var error: String? = null,
    var message: String? = null
) : Parcelable
