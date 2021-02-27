package com.github.myseries.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.myseries.data.model.SearchResponse
import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.model.NetworkException
import com.github.myseries.util.CoroutineDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class SeriesRepositoryTest {
    @get:Rule
    var coroutineDispatcherRule = CoroutineDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val service: TVMazeService = mockk()

    private lateinit var repository: SeriesRepository

    @Before
    fun setup() {
        repository = RealSeriesRepository(service)
    }

    @Test
    fun `should return the search result`() = runBlockingTest {
        val expected = listOf<SearchResponse>()
        coEvery { service.getShowsByName("teste") } returns expected

        val actual = repository.getSeriesByName("teste")

        assert(expected == actual)
    }

    @Test(expected = NetworkException.Connection::class)
    fun `should throw connection exception`() = runBlockingTest {
        coEvery { service.getShowsByName("teste") } throws IOException()

        repository.getSeriesByName("teste")
    }

    @Test(expected = NetworkException.Server::class)
    fun `should throw server exception`() = runBlockingTest {
        val httpException = mockk<HttpException>()
        coEvery { service.getShowsByName("teste") } throws httpException

        repository.getSeriesByName("teste")
    }
}