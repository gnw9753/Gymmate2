package com.example.gymmate.homepage;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gymmate.homepage.Homepage
import com.example.gymmate.databinding.FragHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { Homepage() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}