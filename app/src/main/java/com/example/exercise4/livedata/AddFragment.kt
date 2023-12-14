package com.example.exercise4.livedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.exercise4.R
import com.example.exercise4.data.Champion
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentAddBinding
import com.example.exercise4.databinding.FragmentChampionAddDbBinding
import com.example.exercise4.livedata.List4Fragment
import com.example.exercise4.livedata.ListRepository

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)

        return binding.root
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
        val bundle = Bundle().apply {
            putString("name", name)
            putString("description", description)
            putInt("lane", lane)
            putFloat("rating", rating)
        }

        parentFragmentManager.setFragmentResult("item_added", bundle)
        findNavController().navigateUp()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
