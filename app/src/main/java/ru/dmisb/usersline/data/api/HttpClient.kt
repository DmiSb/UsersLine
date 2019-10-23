package ru.dmisb.usersline.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {
    fun <S> createService(serviceClass: Class<S>) : S {
        val retrofit = createRetrofitInstance(httpClient)
        return retrofit.create(serviceClass)
    }

    private val httpClient by lazy { createHttpClientInstance() }

    private fun createHttpClientInstance() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(HttpConfig.MAX_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HttpConfig.MAX_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HttpConfig.MAX_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    private fun createRetrofitInstance(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(HttpConfig.BASE_URL)
            .client(client)
            .addConverterFactory(createConverterFactory())
            .build()
    }

    private fun createConverterFactory() : Converter.Factory {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        return GsonConverterFactory.create(gson)
    }
}