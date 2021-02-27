package com.github.myseries.ui.series

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.github.myseries.MyApplication
import com.github.myseries.R
import com.github.myseries.databinding.FragmentSeriesBinding
import com.github.myseries.di.ComposableRoot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesFragment : Fragment() {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private val factory: SeriesViewModelFactory by lazy { (requireActivity().application as ComposableRoot).appCompositionRoot.seriesViewModelFactory }
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

        val adapter = setupSeriesAdapter()

        viewModel = ViewModelProvider(this, factory)
            .get(SeriesViewModel::class.java)

        setSeriesObserver(adapter)
        setHasOptionsMenu(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun setSeriesObserver(adapter: SeriesAdapter) {
        scope.launch {
            viewModel.getSeries().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupSeriesAdapter(): SeriesAdapter {
        val adapter = SeriesAdapter()

        binding.retryButton.setOnClickListener { adapter.retry() }
        binding.listShows.layoutManager = GridLayoutManager(requireContext(), 2)

        setLoadStateListener(adapter)

        binding.listShows.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SeriesLoadStateAdapter { adapter.retry() },
            footer = SeriesLoadStateAdapter { adapter.retry() }
        )
        return adapter
    }

    private fun setLoadStateListener(adapter: SeriesAdapter) {
        adapter.addLoadStateListener { loadState ->

            binding.listShows.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                showErrorMessage(it)
            }

        }
    }

    private fun showErrorMessage(it: LoadState.Error) {
        Toast.makeText(
            requireContext(),
            "Check your connection, please. ${it.error}",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search -> {
                findNavController().navigate(R.id.to_search)
            }
        }
        return true
    }
}
