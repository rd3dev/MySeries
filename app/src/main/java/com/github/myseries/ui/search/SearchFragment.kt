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
import com.github.myseries.MyApplication
import com.github.myseries.databinding.FragmentSearchBinding
import com.github.myseries.di.ComposableRoot

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(SearchViewModel::class.java)
        binding.listSearchResult.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listSearchResult.adapter = searchAdapter

        binding.query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchSeriesByName(binding.query.text.toString())
                binding.query.onEditorAction(EditorInfo.IME_ACTION_DONE)
            }
            true
        }

        binding.search.setOnClickListener {
            viewModel.searchSeriesByName(binding.query.text.toString())
            binding.query.onEditorAction(EditorInfo.IME_ACTION_DONE)
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
