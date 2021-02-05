package com.example.lesson5_rxjavatest.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkManager {

    val naverApi by lazy { retrofit.create<NaverApi>() }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://openapi.naver.com/")
            .build()
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", "y5_NNkPCE9ItVzDN4SYN")
                    .addHeader("X-Naver-Client-Secret", "dVE5yklLXs")
                    .build()
                    .let { request ->
                        chain.proceed(request)
                    }
            }
            .build()
    }

}