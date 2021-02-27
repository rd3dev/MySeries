package com.github.myseries.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.domain.model.NetworkException
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SeriesRepository) : ViewModel() {
    val viewState = MutableLiveData<SearchViewState>()

    fun searchSeriesByName(it: String) {
        viewState.value = SearchViewState.Loading
        viewModelScope.launch {
            try {
                val result = repository.getSeriesByName(it)
                if(result.isEmpty()) {
                    viewState.value = SearchViewState.EmptyLoaded
                } else {
                    viewState.value = SearchViewState.Loaded(result)
                }
            } catch (e: NetworkException) {
                viewState.value = SearchViewState.LoadFailed(e)
            }
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    data class LoadFailed(val exception: NetworkException) : SearchViewState()
    object EmptyLoaded : SearchViewState()
    data class Loaded(val shows: List<Series>) : SearchViewState()
}