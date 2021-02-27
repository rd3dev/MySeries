package com.github.myseries.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.myseries.domain.model.NetworkException
import com.github.myseries.domain.model.Series
import com.github.myseries.fake.FakeSeriesRepository
import com.github.myseries.util.CoroutineDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineDispatcherRule = CoroutineDispatcherRule()

    private val repository = FakeSeriesRepository()

    @Before
    fun setup() {
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `should emit the search result`() {
        viewModel.searchSeriesByName("girl")

        viewModel.viewState.value = SearchViewState.Loaded(repository.result as List<Series>)
    }

    @Test
    fun `should emit connection error`() {
        repository.result = IOException()

        viewModel.searchSeriesByName("girl")

        viewModel.viewState.value = SearchViewState.LoadFailed(NetworkException.Connection)
    }

    @Test
    fun `should emit server error`() {
        repository.result = IOException()

        viewModel.searchSeriesByName("girl")

        viewModel.viewState.value = SearchViewState.LoadFailed(NetworkException.Server)
    }

    @Test
    fun `should emit empty search result`() {
        repository.result = listOf<Series>()

        viewModel.searchSeriesByName("girl")

        viewModel.viewState.value = SearchViewState.EmptyLoaded
    }

}