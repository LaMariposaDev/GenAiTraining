package com.dev.lamariposa.boardgamesassociation.presentation.ui.details

import android.app.AlertDialog
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev.lamariposa.boardgamesassociation.R
import com.dev.lamariposa.boardgamesassociation.databinding.DialogAddToMyGamesBinding
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentBoardGameDetailBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AddToCollectionStatus
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.BoardGameDetailUiState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.BoardGameDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardGameDetailFragment : Fragment() {
    
    private val viewModel: BoardGameDetailViewModel by viewModel()
    private var _binding: FragmentBoardGameDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BoardGameDetailFragmentArgs>()
    
    private var currentBoardGameDetail: BoardGameDetail? = null
    private var persons: List<Person> = emptyList()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val boardGameId = args.boardGameId
        
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BoardGameDetailUiState.Loading -> showLoading()
                is BoardGameDetailUiState.Success -> {
                    currentBoardGameDetail = state.boardGameDetail
                    showBoardGameDetails(state.boardGameDetail)
                }
                is BoardGameDetailUiState.Error -> showError(state.message)
            }
        }
        
        // Load persons for the spinners
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.persons.collect { personsList ->
                persons = personsList
            }
        }
        
        // Monitor add to collection status
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addToCollectionStatus.collect { status ->
                when (status) {
                    is AddToCollectionStatus.Loading -> {
                        // Show loading indicator if needed
                    }
                    is AddToCollectionStatus.Success -> {
                        Toast.makeText(requireContext(), "Added to My Games", Toast.LENGTH_SHORT).show()
                        viewModel.resetAddToCollectionStatus()
                    }
                    is AddToCollectionStatus.Error -> {
                        Toast.makeText(requireContext(), "Error: ${status.message}", Toast.LENGTH_LONG).show()
                        viewModel.resetAddToCollectionStatus()
                    }
                    else -> {} // Idle state, do nothing
                }
            }
        }
        
        binding.retryButton.setOnClickListener {
            viewModel.loadBoardGameDetails(boardGameId)
        }
        
        binding.addToMyGamesButton.setOnClickListener {
            showAddToMyGamesDialog()
        }
        
        viewModel.loadBoardGameDetails(boardGameId)
        viewModel.loadUsers()
    }
    
    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            contentGroup.visibility = View.GONE
            errorGroup.visibility = View.GONE
        }
    }
    
    private fun showBoardGameDetails(boardGameDetail: BoardGameDetail) {
        binding.apply {
            progressBar.visibility = View.GONE
            contentGroup.visibility = View.VISIBLE
            errorGroup.visibility = View.GONE
            
            titleTextView.text = boardGameDetail.name
            yearTextView.text = "Year: ${boardGameDetail.yearPublished ?: "N/A"}"
            playersTextView.text = "Players: ${boardGameDetail.minPlayers}-${boardGameDetail.maxPlayers}"
            playingTimeTextView.text = "Playing Time: ${boardGameDetail.playingTime} min"
            typeTextView.text = "Type: ${boardGameDetail.type}"
            
            // Use HTML formatting for description if available
            descriptionTextView.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(boardGameDetail.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(boardGameDetail.description)
            }
            
            // Load image with Glide
            boardGameDetail.imageUrl?.let { imageUrl ->
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(boardGameImageView)
            } ?: run {
                boardGameImageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
    
    private fun showError(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            contentGroup.visibility = View.GONE
            errorGroup.visibility = View.VISIBLE
            errorTextView.text = message
            Log.v("BoardGameDetailFragment", "Error: $message")
        }
    }
    
    private fun showAddToMyGamesDialog() {
        val dialogBinding = DialogAddToMyGamesBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        // Setup spinner adapters
        setupPersonsSpinner(dialogBinding.ownerSpinner)
        setupPersonsSpinner(dialogBinding.holderSpinner)
        
        // Hide the add person button since we're using Firebase users
        dialogBinding.addOwnerButton.visibility = View.GONE
        
        // Select current user as owner by default
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentUserPerson.collect { currentUser ->
                if (currentUser != null) {
                    val position = persons.indexOfFirst { it.id == currentUser.id }
                    if (position >= 0) {
                        dialogBinding.ownerSpinner.setSelection(position + 1) // +1 for "None" option
                    }
                }
            }
        }

        // Handle save button
        dialogBinding.saveButton.setOnClickListener {
            val boardGameDetail = currentBoardGameDetail ?: return@setOnClickListener

            // Get selected owner
            val ownerId = if (dialogBinding.ownerSpinner.selectedItemPosition > 0) {
                persons[dialogBinding.ownerSpinner.selectedItemPosition - 1].id
            } else null

            // Get selected holder
            val holderId = if (dialogBinding.holderSpinner.selectedItemPosition > 0) {
                persons[dialogBinding.holderSpinner.selectedItemPosition - 1].id
            } else null

            // Get notes
            val notes = dialogBinding.notesEditText.text.toString().trim()
                .takeIf { it.isNotEmpty() }

            // Add to collection
            viewModel.addToMyCollection(boardGameDetail, ownerId, holderId, notes)
            dialog.dismiss()
        }

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupPersonsSpinner(spinner: android.widget.Spinner) {
        val items = listOf("None") + persons.map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_person_spinner,
            items
        )
        spinner.adapter = adapter
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
