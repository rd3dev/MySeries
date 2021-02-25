package com.github.myseries.ui.series.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.myseries.MySeriesApplication
import com.github.myseries.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private val factory: SearchViewModelFactory by lazy { (requireActivity().application as MySeriesApplication).seriesContainer.searchViewModelFactory }

    private lateinit var binding: FragmentSearchBinding
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(SearchViewModel::class.java)
        binding.listSearchResult.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listSearchResult.adapter = searchAdapter

        binding.query.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchSeriesByName(binding.query.text.toString())
                true
            }
            false
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            binding.progressBar.isVisible = viewState is SearchViewState.Loading
            when (viewState) {
                is SearchViewState.Loaded -> {
                    searchAdapter.setShows(viewState.shows)
                }

            }
        }
    }
}
