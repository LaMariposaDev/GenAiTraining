package com.dev.lamariposa.boardgamesassociation.presentation.ui.myGames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentMyGamesBinding
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.MyGamesViewModel

class MyGamesFragment : Fragment() {

    private var _binding: FragmentMyGamesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myGamesViewModel =
            ViewModelProvider(this).get(MyGamesViewModel::class.java)

        _binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyGames
        myGamesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}