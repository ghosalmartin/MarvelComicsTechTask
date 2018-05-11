package com.example.mgh01.techtask.repositories

import com.example.mgh01.techtask.utils.TestSchedulerRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.paperdb.Book
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PreviousSearchesRepositoryTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    @Mock
    private lateinit var mockBook: Book

    private lateinit var repo: PreviousSearchesRepository

    @Before
    fun setUp() {
        repo = PreviousSearchesRepository(mockBook)
    }

    @Test
    fun test_insert_search_writes_to_book_with_no_duplicates() {
        //Given
        val searchEntry = "test"
        val listOfEntries = listOf(searchEntry)
        whenever(mockBook.read<List<String>>(any(), any())).thenReturn(listOfEntries)

        //When
        repo.insertSearch(searchEntry).test()
        testRule.triggerActions()

        //Then
        verify(mockBook).read("PREVIOUS_SEARCHES", emptyList<String>())
        verify(mockBook).write("PREVIOUS_SEARCHES", listOfEntries)
    }
}