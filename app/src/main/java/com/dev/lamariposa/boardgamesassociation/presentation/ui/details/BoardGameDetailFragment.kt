package com.dev.lamariposa.boardgamesassociation.presentation.ui.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev.lamariposa.boardgamesassociation.R
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentBoardGameDetailBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGameDetail
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.BoardGameDetailUiState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.BoardGameDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardGameDetailFragment : Fragment() {
    
    private val viewModel: BoardGameDetailViewModel by viewModel()
    private var _binding: FragmentBoardGameDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BoardGameDetailFragmentArgs>()
    
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
                is BoardGameDetailUiState.Success -> showBoardGameDetails(state.boardGameDetail)
                is BoardGameDetailUiState.Error -> showError(state.message)
            }
        }
        
        binding.retryButton.setOnClickListener {
            viewModel.loadBoardGameDetails(boardGameId)
        }
        
        viewModel.loadBoardGameDetails(boardGameId)
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
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
