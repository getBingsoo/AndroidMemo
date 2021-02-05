package com.example.lesson5_rxjavatest.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * lesson5_RxJavaTest
 * NaverApi.kt
 * Description:
 *
 * Created by Lina on 2020-09-16
 */

interface NaverApi {
    @GET("v1/search/movie.json")
    fun getMovies(@Query("query") query: String): Single<NaverMovieResponse>
}
