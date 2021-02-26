package com.github.myseries.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SeriesRepository) : ViewModel() {

    val viewState = MutableLiveData<SearchViewState>()

    fun searchSeriesByName(it: String) {
        viewState.value = SearchViewState.Loading
        viewModelScope.launch {
            val result = repository.getSeriesByName(it)
            viewState.value = SearchViewState.Loaded(result)
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object LoadFailed : SearchViewState()//TODO
    data class Loaded(val shows: List<Series>) : SearchViewState()
}