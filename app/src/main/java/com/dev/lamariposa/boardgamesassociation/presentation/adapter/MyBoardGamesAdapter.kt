package com.dev.lamariposa.boardgamesassociation.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.lamariposa.boardgamesassociation.R
import com.dev.lamariposa.boardgamesassociation.databinding.ItemMyBoardGameBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.MyBoardGame

class MyBoardGamesAdapter(
    private val onItemClick: (MyBoardGame) -> Unit,
    private val onRemoveClick: (MyBoardGame) -> Unit
) : ListAdapter<MyBoardGame, MyBoardGamesAdapter.MyBoardGameViewHolder>(MyBoardGameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBoardGameViewHolder {
        val binding = ItemMyBoardGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyBoardGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyBoardGameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyBoardGameViewHolder(
        private val binding: ItemMyBoardGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.removeButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRemoveClick(getItem(position))
                }
            }
        }

        fun bind(myBoardGame: MyBoardGame) {
            binding.apply {
                gameTitleTextView.text = myBoardGame.boardGame.name
                gameTypeTextView.text = myBoardGame.boardGame.type
                
                ownerTextView.text = if (myBoardGame.owner != null) {
                    "Owner: ${myBoardGame.owner.name}"
                } else {
                    "Owner: None"
                }
                
                holderTextView.text = if (myBoardGame.holder != null) {
                    "Holder: ${myBoardGame.holder.name}"
                } else {
                    "Holder: None"
                }

                // Load image with Glide
                myBoardGame.boardGame.imageUrl?.let { imageUrl ->
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(gameImageView)
                } ?: run {
                    gameImageView.setImageResource(R.drawable.placeholder_image)
                }
            }
        }
    }

    class MyBoardGameDiffCallback : DiffUtil.ItemCallback<MyBoardGame>() {
        override fun areItemsTheSame(oldItem: MyBoardGame, newItem: MyBoardGame): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyBoardGame, newItem: MyBoardGame): Boolean {
            return oldItem == newItem
        }
    }
}
