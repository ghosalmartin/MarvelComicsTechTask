package com.example.mgh01.techtask.repositories

import com.example.mgh01.techtask.models.MarvelComicSearchResponse
import com.example.mgh01.techtask.services.MarvelService
import com.example.mgh01.techtask.utils.BuildConstants
import com.example.mgh01.techtask.utils.Network
import com.example.mgh01.techtask.utils.md5
import io.reactivex.Single

class MarvelComicRepository(
    private val service: MarvelService = Network.createService(MarvelService::class.java, BuildConstants.MARVEL_BASE_URL),
    private val time: Long = System.currentTimeMillis()) {

    fun searchForUsers(searchTerm: String): Single<MarvelComicSearchResponse> {
        val hash: String = "$time${BuildConstants.MARVEL_PRIVATE_KEY}${BuildConstants.MARVEL_PUBLIC_KEY}".md5().toLowerCase()
        return service.searchForComic(time, searchTerm, BuildConstants.MARVEL_PUBLIC_KEY, hash)
    }
}