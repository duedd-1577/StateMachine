package com.danhdueexoictif.statemachine.data.remote

import com.danhdueexoictif.statemachine.data.remote.response.ErrorResponse
import com.danhdueexoictif.statemachine.data.remote.response.LogoutResponse
import com.danhdueexoictif.statemachine.data.remote.response.OauthToken
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    /**************************************************
     * =============== AUTHENTICATION =================
     **************************************************/
    @POST("oauth/token")
    @FormUrlEncoded
    fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: Int,
        @Field("client_secret") clientSecret: String,
        @Field("service") provider: String,
        @Field("token") accessToken: String
    ): Deferred<NetworkResponse<OauthToken, ErrorResponse>>

    @POST("oauth/token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: Int,
        @Field("client_secret") clientSecret: String,
        @Field("refresh_token") refreshToken: String
    ): Call<OauthToken>

    @POST("logout")
    @FormUrlEncoded
    suspend fun logout(
        @Header("Authorization") token: String,
        @Field("fcmToken") fcmToken: String?
    ): NetworkResponse<LogoutResponse, ErrorResponse>
}
