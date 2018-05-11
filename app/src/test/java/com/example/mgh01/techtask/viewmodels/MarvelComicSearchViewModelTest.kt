package com.example.mgh01.techtask.viewmodels

import com.example.mgh01.techtask.models.MarvelComicSearchItem
import com.example.mgh01.techtask.models.MarvelComicSearchResponse
import com.example.mgh01.techtask.repositories.MarvelComicRepository
import com.example.mgh01.techtask.repositories.PreviousSearchesRepository
import com.example.mgh01.techtask.utils.TestSchedulerRule
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class MarvelComicSearchViewModelTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    @Mock
    private lateinit var mockMarvelComicRepo: MarvelComicRepository

    @Mock
    private lateinit var mockPreviousSearchesRepository: PreviousSearchesRepository

    private lateinit var viewModel: MarvelComicSearchViewModel

    @Before
    fun setup() {
        viewModel = MarvelComicSearchViewModel(mockMarvelComicRepo, mockPreviousSearchesRepository)
    }

    @Test
    fun test_success_flow() {
        //Given
        val input = "test"
        val flowable = Flowable.just(input)
        whenever(mockMarvelComicRepo.searchForUsers(input))
            .thenReturn(Single.just(MarvelComicSearchResponse(MarvelComicSearchItem(emptyList()))))
        whenever(mockPreviousSearchesRepository.insertSearch(input))
            .thenReturn(Flowable.just(emptyList()))

        //When
        viewModel.getComicResultsPublisher(flowable).test()
        testRule.triggerActions()

        //Then
        verify(mockMarvelComicRepo).searchForUsers(input)
        verify(mockPreviousSearchesRepository).insertSearch(input)
    }

    @Test
    fun test_error_flow_falls_back_to_empty_results() {
        //Given
        val input = "test"
        val flowable = Flowable.just(input)
        whenever(mockMarvelComicRepo.searchForUsers(input))
            .thenReturn(Single.error(SocketTimeoutException()))
        whenever(mockPreviousSearchesRepository.insertSearch(input))
            .thenReturn(Flowable.just(emptyList()))

        //When
        val testSubscriber = viewModel.getComicResultsPublisher(flowable).test()
        testRule.triggerActions()

        //Then
        verify(mockMarvelComicRepo).searchForUsers(input)
        verify(mockPreviousSearchesRepository).insertSearch(input)

        testSubscriber.assertNoErrors()
    }

    @Test
    fun test_insert_search_error_flow_falls_back_to_empty_results() {
        //Given
        val input = "test"
        val flowable = Flowable.just(input)
        whenever(mockPreviousSearchesRepository.insertSearch(input))
            .thenReturn(Flowable.error(Exception()))

        //When
        val testSubscriber = viewModel.getComicResultsPublisher(flowable).test()
        testRule.triggerActions()

        //Then
        verify(mockPreviousSearchesRepository).insertSearch(input)
        testSubscriber.assertNoErrors()
    }

    @Test
    fun test_get_previous_searches_makes_appropriate_repo_call() {
        //Given
        whenever(mockPreviousSearchesRepository.getSearches()).thenReturn(Flowable.just(emptyList()))

        //When
        viewModel.getPreviousSearches().test()
        testRule.triggerActions()

        //Then
        verify(mockPreviousSearchesRepository).getSearches()
    }

    @Test
    fun test_get_previous_searches_on_error_returns_empty_list() {
        //Given
        whenever(mockPreviousSearchesRepository.getSearches()).thenReturn(Flowable.error(Exception()))

        //When
        val testSubscriber = viewModel.getPreviousSearches().test()
        testRule.triggerActions()

        //Then
        testSubscriber.assertNoErrors()
    }
}
