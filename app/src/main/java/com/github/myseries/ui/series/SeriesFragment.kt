package com.github.myseries.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.github.myseries.MySeriesApplication
import com.github.myseries.databinding.FragmentSeriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesFragment : Fragment() {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private val factory: SeriesViewModelFactory by lazy { (requireActivity().application as MySeriesApplication).seriesContainer.seriesViewModelFactory }
    private lateinit var viewModel: SeriesViewModel

    private lateinit var binding: FragmentSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SeriesAdapter()

        binding.retryButton.setOnClickListener { adapter.retry() }

        binding.listShows.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.addLoadStateListener { loadState ->

            binding.listShows.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "Check your connection, please. ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.listShows.adapter =  adapter.withLoadStateHeaderAndFooter(
            header = SeriesLoadStateAdapter { adapter.retry() },
            footer = SeriesLoadStateAdapter { adapter.retry() }
        )

        viewModel = ViewModelProvider(this, factory)
            .get(SeriesViewModel::class.java)

        scope.launch {
            viewModel.getSeries().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}