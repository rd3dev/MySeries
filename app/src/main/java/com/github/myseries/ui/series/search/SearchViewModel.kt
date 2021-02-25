package com.github.myseries.ui.series.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.myseries.data.model.Show
import com.github.myseries.domain.ShowRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ShowRepository) : ViewModel() {

    val viewState = MutableLiveData<SearchViewState>()

    fun searchSeriesByName(it: String) {
        viewState.value = SearchViewState.Loading
        viewModelScope.launch {
            val result = repository.getShowsByNameStream(it)
            viewState.value = SearchViewState.Loaded(result)
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object LoadFailed : SearchViewState()
    data class Loaded(val shows: List<Show>) : SearchViewState()
}