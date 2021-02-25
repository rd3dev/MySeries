package com.github.myseries.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.myseries.MainActivity
import com.github.myseries.MySeriesApplication
import com.github.myseries.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesFragment : Fragment() {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private val factory: SeriesViewModelFactory by lazy { (requireActivity().application as MySeriesApplication).seriesContainer.seriesViewModelFactory }
    private lateinit var viewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_shows)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = SeriesAdapter()
        recyclerView.adapter = adapter

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