package com.example.mgh01.techtask.viewmodels

import android.arch.lifecycle.ViewModel
import com.example.mgh01.techtask.extensions.onDefaultSchedulers
import com.example.mgh01.techtask.models.MarvelComicResult
import com.example.mgh01.techtask.models.MarvelComicSearchItem
import com.example.mgh01.techtask.models.MarvelComicSearchResponse
import com.example.mgh01.techtask.repositories.MarvelComicRepository
import com.example.mgh01.techtask.repositories.PreviousSearchesRepository
import io.reactivex.Flowable

class MarvelComicSearchViewModel(
    private val repo: MarvelComicRepository = MarvelComicRepository(),
    private val previousSearchesRepository: PreviousSearchesRepository = PreviousSearchesRepository()) : ViewModel() {

    fun getComicResultsPublisher(searchFlowable: Flowable<String>): Flowable<List<MarvelComicResult>> =
        searchFlowable
            .flatMap { searchTerm ->
                previousSearchesRepository.insertSearch(searchTerm).flatMapSingle {
                    repo.searchForUsers(searchTerm).onErrorReturn { emptyResult() }
                }.onErrorReturn { emptyResult() }
            }
            .onDefaultSchedulers()
            .map { it.data.results }

    fun getPreviousSearches(): Flowable<List<String>> = previousSearchesRepository.getSearches().onErrorReturn { emptyList() }

    private fun emptyResult() = MarvelComicSearchResponse(MarvelComicSearchItem(emptyList()))
}
