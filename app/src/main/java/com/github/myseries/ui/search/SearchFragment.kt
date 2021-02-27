package com.github.myseries.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.myseries.R
import com.github.myseries.databinding.FragmentSearchBinding
import com.github.myseries.di.ComposableRoot
import com.github.myseries.domain.model.NetworkException

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private val factory: SearchViewModelFactory by lazy {
        (requireActivity().application as ComposableRoot).appCompositionRoot.searchViewModelFactory
    }

    private lateinit var binding: FragmentSearchBinding
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(SearchViewModel::class.java)

        setupRecyclerView()
        setActionSearchListener()
        setSearchButtonListener()
        setViewStateObserver()
    }

    private fun setViewStateObserver() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            binding.progressBar.isVisible = viewState is SearchViewState.Loading
            binding.textError.isVisible = viewState is SearchViewState.LoadFailed
            binding.textNoSeriesFound.isVisible = viewState is SearchViewState.EmptyLoaded
            binding.listSearchResult.isVisible = viewState is SearchViewState.Loaded
            when (viewState) {
                is SearchViewState.Loaded -> {
                    searchAdapter.setShows(viewState.shows)
                }
                is SearchViewState.LoadFailed -> {
                    handlerException(viewState)
                }
            }
        }
    }

    private fun handlerException(viewState: SearchViewState.LoadFailed) {
        when (viewState.exception) {
            is NetworkException.Connection -> {
                binding.textError.text = getString(R.string.connection_error)
            }
            is NetworkException.Server -> {
                binding.textError.text = getString(R.string.server_error)
            }
        }
    }

    private fun setSearchButtonListener() {
        binding.search.setOnClickListener {
            doSearch()
        }
    }

    private fun setupRecyclerView() {
        binding.listSearchResult.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listSearchResult.adapter = searchAdapter
    }

    private fun setActionSearchListener() {
        binding.query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
            }
            true
        }
    }

    private fun doSearch() {
        viewModel.searchSeriesByName(binding.query.text.toString())
        binding.query.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }
}

