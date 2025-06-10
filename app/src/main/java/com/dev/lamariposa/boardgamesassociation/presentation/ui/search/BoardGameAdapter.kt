package com.dev.lamariposa.boardgamesassociation.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.lamariposa.boardgamesassociation.databinding.ItemBoardGameBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.BoardGame

class BoardGameAdapter(
    private val onItemClick: (BoardGame) -> Unit
) : ListAdapter<BoardGame, BoardGameAdapter.BoardGameViewHolder>(BoardGameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardGameViewHolder {
        val binding = ItemBoardGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BoardGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardGameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BoardGameViewHolder(
        private val binding: ItemBoardGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(boardGame: BoardGame) {
            binding.textGameName.text = boardGame.name
            binding.textGameYear.text = boardGame.yearPublished?.toString() ?: "Year unknown"
        }
    }

    private class BoardGameDiffCallback : DiffUtil.ItemCallback<BoardGame>() {
        override fun areItemsTheSame(oldItem: BoardGame, newItem: BoardGame): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BoardGame, newItem: BoardGame): Boolean {
            return oldItem == newItem
        }
    }
}
