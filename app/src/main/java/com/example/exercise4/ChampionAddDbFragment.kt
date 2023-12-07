package com.example.exercise4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.exercise4.data.Champion
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentChampionAddDbBinding


class ChampionAddDbFragment : Fragment() {
    lateinit var binding: FragmentChampionAddDbBinding
    private lateinit var dataRepo: ChampionRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChampionAddDbBinding.inflate(layoutInflater)

        return binding.root
//        return inflater.inflate(R.layout.fragment_champion_add_db, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButtondb.setOnClickListener {
            onSaveButtonClick()
        }

        binding.cancelButtondb.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun onSaveButtonClick() {
        dataRepo = ChampionRepository.getInstance(requireContext())!!
        val name = binding.editNamedb.text.toString()
        val description = binding.editTextTextMultiLinedb.text.toString()

        val selectedLaneId = binding.radioGroupdb.checkedRadioButtonId
        Log.d("ChampionAddDbFragment", "selectedLaneId: $selectedLaneId")
        val lane = when (selectedLaneId) {
            R.id.topRadioButtondb -> 0
            R.id.jungleRadioButtondb -> 1
            R.id.midRadioButtondb -> 2
            R.id.botRadioButtondb -> 3
            R.id.supportRadioButtondb -> 4
            else -> 0
        }

        val rating = binding.ratingBar3db.rating

        val champion = Champion(name, description, lane, rating)
        Log.d("ChampionAddDbFragment", "champion: $champion")


        dataRepo.addItem(champion)
        parentFragmentManager.setFragmentResult("item_added", Bundle.EMPTY)
        findNavController().navigateUp()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChampionAddDbFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}