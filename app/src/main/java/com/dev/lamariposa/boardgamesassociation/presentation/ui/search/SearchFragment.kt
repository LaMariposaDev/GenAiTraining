package com.dev.lamariposa.boardgamesassociation.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentSearchBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.SearchUiState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var boardGameAdapter: BoardGameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupSearchView()
        observeViewModel()
        Log.v("TAG", "SearchFragment onViewCreated called")
    }
    
    private fun setupRecyclerView() {
        boardGameAdapter = BoardGameAdapter { boardGame ->
            onBoardGameClicked(boardGame)
        }
        
        binding.recyclerView.apply {
            adapter = boardGameAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchViewModel.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally, you can implement search-as-you-type here
                return false
            }
        })
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect { state ->
                    updateUi(state)
                }
            }
        }
    }
    
    private fun updateUi(state: SearchUiState) {
        when (state) {
            is SearchUiState.Initial -> {
                binding.progressBar.visibility = View.GONE
                binding.textError.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
            }
            is SearchUiState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.textError.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
            }
            is SearchUiState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.textError.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                boardGameAdapter.submitList(state.games)
            }
            is SearchUiState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = state.message
            }
        }
    }
    
    private fun onBoardGameClicked(boardGame: BoardGame) {
        // Navigate to the board game details screen
        val action = SearchFragmentDirections.actionSearchFragmentToBoardGameDetailFragment(boardGame.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
