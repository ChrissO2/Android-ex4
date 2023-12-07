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
import com.example.exercise4.data.ChampionRepository

class ChampionDetailFragment : Fragment() {
    lateinit var binding: FragmentChampionDetailBinding
    private lateinit var dataRepo: ChampionRepository

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

        binding.updateSaveButton.setOnClickListener {
            onSaveButtonClick()
        }
    }

    private fun onSaveButtonClick() {
        dataRepo = ChampionRepository.getInstance(requireContext())!!
        val championId = arguments?.getInt("id")
        if (championId != null) {
            val champion = dataRepo.getItemById(championId)
            champion?.apply {
                this.name = binding.updateName.text.toString()
                this.description = binding.updateDesc.text.toString()
                this.rating = binding.updateRating.rating
                val selectedLaneId = binding.radioGroup2.checkedRadioButtonId
                when (selectedLaneId) {
                    R.id.radioButtonTop -> this.lane = 0
                    R.id.radioButtonJg -> this.lane = 1
                    R.id.radioButtonMid -> this.lane = 2
                    R.id.radioButtonBot -> this.lane = 3
                    R.id.radioButtonSupp -> this.lane = 4
                    else -> this.lane = 0
                }
                dataRepo.updateItem(this)
                Toast.makeText(requireContext(), "Item updated in the database", Toast.LENGTH_SHORT).show()
//                parentFragmentManager.setFragmentResult("item_updated", Bundle.EMPTY)
                parentFragmentManager.setFragmentResult("item_added", Bundle.EMPTY)
                findNavController().navigateUp()
            }
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