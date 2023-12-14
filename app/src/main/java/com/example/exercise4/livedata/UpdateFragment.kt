package com.example.exercise4.livedata

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.exercise4.R
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val name = bundle!!.getString("name", "Default name")
        val description = bundle!!.getString("description", "No description")
        val lane = bundle!!.getInt("lane", 0)
        val rating = bundle!!.getFloat("rating", 1.0F)
        val position = bundle!!.getInt("position", 0)

        binding.updateName.text = Editable.Factory.getInstance().newEditable(name)
        binding.updateDesc.text = Editable.Factory.getInstance().newEditable(description)
        binding.updateRating.rating = rating

        when (lane) {
            0 -> binding.radioButtonTop.isChecked = true
            1 -> binding.radioButtonJg.isChecked = true
            2 -> binding.radioButtonMid.isChecked = true
            3 -> binding.radioButtonBot.isChecked = true
            4 -> binding.radioButtonSupp.isChecked = true
        }

        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updateName.text.toString()
            val newDesc = binding.updateDesc.text.toString()
            val newRating = binding.updateRating.rating
            var newLane = 0
            when (binding.radioGroup2.checkedRadioButtonId) {
                R.id.radioButtonTop -> newLane = 0
                R.id.radioButtonJg -> newLane = 1
                R.id.radioButtonMid -> newLane = 2
                R.id.radioButtonBot -> newLane = 3
                R.id.radioButtonSupp -> newLane = 4
            }
            val bundle = Bundle()
            bundle.putString("name", newName)
            bundle.putString("description", newDesc)
            bundle.putInt("lane", newLane)
            bundle.putFloat("rating", newRating)
            bundle.putInt("position", position)
            Log.d("UpdateFragment", "onViewCreated: " + bundle.toString())
            parentFragmentManager.setFragmentResult("item_updated", bundle)
            findNavController().popBackStack(R.id.nav_list_view_db, false)
        }

        binding.updateCancelBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("description", description)
            bundle.putInt("lane", lane)
            bundle.putFloat("rating", rating)
            bundle.putBoolean("edit", false)
            parentFragmentManager.setFragmentResult("not_edited", bundle)
            findNavController().navigateUp()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}