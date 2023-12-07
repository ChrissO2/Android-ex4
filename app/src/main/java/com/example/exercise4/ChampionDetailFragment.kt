package com.example.exercise4

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.exercise4.databinding.FragmentChampionAddDbBinding
import com.example.exercise4.databinding.FragmentChampionDetailBinding
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.exercise4.data.Champion

class ChampionDetailFragment : Fragment() {
    lateinit var binding: FragmentChampionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChampionDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateName.text = Editable.Factory.getInstance().newEditable(arguments?.getString("name"))
        binding.updateDesc.text = Editable.Factory.getInstance().newEditable(arguments?.getString("description"))
        binding.updateRating.rating = arguments?.getFloat("rating")!!
        var lane = arguments?.getInt("lane")
        when (lane) {
            0 -> binding.radioButtonTop.isChecked = true
            1 -> binding.radioButtonJg.isChecked = true
            2 -> binding.radioButtonMid.isChecked = true
            3 -> binding.radioButtonBot.isChecked = true
            4 -> binding.radioButtonSupp.isChecked = true
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChampionDetailFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}