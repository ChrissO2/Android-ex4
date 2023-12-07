package com.example.exercise4.championlist

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exercise4.databinding.FragmentChampionDetailBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.exercise4.R
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

        dataRepo = ChampionRepository.getInstance(requireContext())!!
        val championId = arguments?.getInt("id")
        val champion = dataRepo.getItemById(championId!!)
        var name = champion?.name!!
        var description = champion?.description!!
        var lane = champion?.lane!!
        var rating = champion?.rating!!

        binding.updateName.text = Editable.Factory.getInstance().newEditable(name)
        binding.updateDesc.text = Editable.Factory.getInstance().newEditable(description)
        binding.updateRating.rating = rating!!

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

        binding.updateCancelBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun onSaveButtonClick() {
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