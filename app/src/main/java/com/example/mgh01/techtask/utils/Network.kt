package com.example.mgh01.techtask.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

    private const val TIMEOUT_VALUE = 30L

    private val jacksonFactory: JacksonConverterFactory = JacksonConverterFactory.create(ignoreUnknownObjectMapper())
    private val rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create()
    private val httpClient = OkHttpClient.Builder().readTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS).connectTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS).build()

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(jacksonFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .client(httpClient)
                    .build()
                    .create(serviceClass)

    private fun ignoreUnknownObjectMapper(): ObjectMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}