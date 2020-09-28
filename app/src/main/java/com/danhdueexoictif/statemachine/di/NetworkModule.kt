package com.danhdueexoictif.statemachine.di

import android.content.Context
import android.util.Base64
import com.danhdueexoictif.statemachine.BuildConfig
import com.danhdueexoictif.statemachine.data.remote.ApiService
import com.danhdueexoictif.statemachine.data.remote.MockInterceptor
import com.danhdueexoictif.statemachine.data.remote.NetworkAdapterFactory
import com.danhdueexoictif.statemachine.data.remote.NoConnectivityException
import com.danhdueexoictif.statemachine.utils.Constants
import com.danhdueexoictif.statemachine.utils.Constants.NetworkSettings.TIMEOUT
import com.danhdueexoictif.statemachine.utils.networkdetection.NoInternetUtils.isNetworkAvailable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Qualifier
annotation class LoggingInterceptor

@Qualifier
annotation class AuthenticationInterceptor

@Qualifier
annotation class MockingInterceptor

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    @LoggingInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    @MockingInterceptor
    @Provides
    @Singleton
    fun provideMockInterceptor(@ApplicationContext context: Context): Interceptor =
        MockInterceptor(context.assets)

    @AuthenticationInterceptor
    @Provides
    @Singleton
    fun provideBaseAuthInterceptor(
        @ApplicationContext context: Context,
        userName: String,
        password: String
    ): Interceptor = Interceptor { chain ->
        val credentials = "$userName:$password"
        val basicAuth =
            "Basic ${Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)}"
        val request = chain.request()
        val newRequest = request.newBuilder().apply {
            url(request.url.newBuilder().build())
            header(Constants.NetworkRequestParam.AUTHORIZATION, basicAuth)
            header(
                Constants.NetworkRequestParam.CONTENT_TYPE,
                Constants.NetworkRequestValue.CONTENT_TYPE
            )
            method(request.method, request.body)
        }.build()
        if (!isNetworkAvailable(context)) {
            throw NoConnectivityException()
        }
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache?,
        @LoggingInterceptor logging: Interceptor,
        @AuthenticationInterceptor basicAuthInterceptor: Interceptor,
        @MockingInterceptor mockInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        addInterceptor(basicAuthInterceptor)
        addInterceptor(logging)
        // mock data for the APIs while a particular API is not implemented yet on Backend side.
        if (BuildConfig.DEBUG) addInterceptor(mockInterceptor)
        cache?.let { cache(cache) }
    }.build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        networkAdapterFactory: NetworkAdapterFactory,
        moshi: Moshi,
        isErrorHandling: Boolean
    ): Retrofit = Retrofit.Builder().apply {
        if (isErrorHandling) {
            addCallAdapterFactory(networkAdapterFactory)
        }
        addConverterFactory(ScalarsConverterFactory.create())
        addConverterFactory(MoshiConverterFactory.create(moshi))
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpClient)
    }.build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
