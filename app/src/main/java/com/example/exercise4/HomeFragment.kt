package com.example.exercise4

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise4.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: SharedPreferences = requireActivity().getSharedPreferences("data", 0)
        val welcome = data.getString("welcome", "Cwiczenie 4")
        binding.welcomeTextView.text = welcome
        val author = data.getString("author", "Krzysztof Otreba 266541")
        binding.authorTextView.text = author
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}