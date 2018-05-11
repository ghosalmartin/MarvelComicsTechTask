package com.example.mgh01.techtask.services

import com.example.mgh01.techtask.models.MarvelComicSearchResponse
import com.example.mgh01.techtask.utils.BuildConstants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("${BuildConstants.MARVEL_API_VERSION}/public/comics")
    fun searchForComic(
        @Query("ts") timeStamp: Long,
        @Query("titleStartsWith") searchTerms: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String): Single<MarvelComicSearchResponse>
}