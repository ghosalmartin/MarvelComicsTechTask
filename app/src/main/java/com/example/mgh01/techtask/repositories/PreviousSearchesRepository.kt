package com.example.mgh01.techtask.repositories

import io.paperdb.Book
import io.paperdb.Paper
import io.reactivex.Flowable

class PreviousSearchesRepository(private val book: Book = Paper.book()) {

    companion object {
        private const val PREVIOUS_SEARCHES_KEY = "PREVIOUS_SEARCHES"
    }

    fun insertSearch(searchEntry: String): Flowable<List<String>> =
        getSearches().doOnNext {
        val previousSearches = it.toMutableList()
        previousSearches.add(searchEntry)
        val filteredSearches = previousSearches.distinct()
        book.write(PREVIOUS_SEARCHES_KEY, filteredSearches)
    }

    fun getSearches(): Flowable<List<String>> = Flowable.fromCallable { book.read(PREVIOUS_SEARCHES_KEY, emptyList<String>()) }
}