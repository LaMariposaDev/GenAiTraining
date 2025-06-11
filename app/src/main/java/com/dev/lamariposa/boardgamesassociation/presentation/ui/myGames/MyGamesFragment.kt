package com.dev.lamariposa.boardgamesassociation.presentation.ui.myGames

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentMyGamesBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame
import com.dev.lamariposa.boardgamesassociation.presentation.adapter.MyBoardGamesAdapter
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.MyGamesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyGamesFragment : Fragment() {

    private var _binding: FragmentMyGamesBinding? = null
    private val myGamesViewModel: MyGamesViewModel by viewModel()
    private lateinit var adapter: MyBoardGamesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeViewModel()
        
        // Load board games when the fragment is created
        myGamesViewModel.loadMyBoardGames()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = MyBoardGamesAdapter(
            onItemClick = { myBoardGame ->
                // Navigate to board game detail
                val action = MyGamesFragmentDirections.actionMyGamesFragmentToBoardGameDetailFragment(myBoardGame.boardGame.id)
                findNavController().navigate(action)
            },
            onRemoveClick = { myBoardGame ->
                showRemoveConfirmationDialog(myBoardGame)
            }
        )
        
        binding.myGamesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyGamesFragment.adapter
        }
    }
    
    private fun observeViewModel() {
        // Observe board games list
        viewLifecycleOwner.lifecycleScope.launch {
            myGamesViewModel.myBoardGames.collect { games ->
                adapter.submitList(games)
                
                // Show empty state if the list is empty
                if (games.isEmpty()) {
                    binding.emptyStateTextView.visibility = View.VISIBLE
                    binding.myGamesRecyclerView.visibility = View.GONE
                } else {
                    binding.emptyStateTextView.visibility = View.GONE
                    binding.myGamesRecyclerView.visibility = View.VISIBLE
                }
            }
        }
        
        // Observe loading state
        viewLifecycleOwner.lifecycleScope.launch {
            myGamesViewModel.loading.collect { isLoading ->
                binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
        
        // Observe error messages
        myGamesViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(errorMessage)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
    
    private fun showRemoveConfirmationDialog(myBoardGame: MyBoardGame) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove Game")
            .setMessage("Are you sure you want to remove ${myBoardGame.boardGame.name} from your collection?")
            .setPositiveButton("Remove") { _, _ ->
                myGamesViewModel.removeBoardGameFromCollection(myBoardGame.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}