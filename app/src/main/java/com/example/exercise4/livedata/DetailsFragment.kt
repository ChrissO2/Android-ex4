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
import com.example.exercise4.data.Champion
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private var hasChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        Log.d("DetailsFragment", "onViewCreated: $bundle")
        var name = bundle!!.getString("name", "Default name")
        var description = bundle!!.getString("description", "No description")
        var lane = bundle!!.getInt("lane", 0)
        var rating = bundle!!.getFloat("rating", 1.0F)
        val position = bundle!!.getInt("position", 0)

//        parentFragmentManager.setFragmentResultListener("item_updated", viewLifecycleOwner) { _, bundle ->
//            name = bundle.getString("name", "New person")
//            description = bundle.getString("description", "Some spec")
//            lane = bundle.getInt("lane", 0)
//            rating = bundle.getFloat("rating", 1.0F)
//            myViewModel.updateChampion(position, name, description, lane, rating)
//            updateViews(name, description, lane, rating)
//            hasChanged = true
//        }

        updateViews(name, description, lane, rating)

        binding.buttonReturn.setOnClickListener {
            if (hasChanged) {
                parentFragmentManager.setFragmentResult("item_updated", bundle)

            }
            findNavController().navigateUp()
        }

        binding.buttonModify.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("description", description)
            bundle.putInt("lane", lane)
            bundle.putFloat("rating", rating)
            bundle.putInt("position", position)

            findNavController().navigate(
                R.id.action_detailsFragment_to_updateFragment,
                bundle
            )
        }
    }

    private fun updateViews(name: String, description: String, lane: Int, rating: Float?) {
        binding.textViewName.text = name
        binding.textViewDescription.text = description
        binding.textViewRating.text = "Rating: " + rating!!.toString()
        when (lane) {
            0 -> {
                binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Top")
                binding.imageViewLine.setImageResource(R.drawable.top_icon)
            }

            1 -> {
                binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Jungle")
                binding.imageViewLine.setImageResource(R.drawable.jungle_icon)
            }

            2 -> {
                binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Mid")
                binding.imageViewLine.setImageResource(R.drawable.mid_icon)
            }

            3 -> {
                binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Bot")
                binding.imageViewLine.setImageResource(R.drawable.adc_icon)
            }

            4 -> {
                binding.textViewLine.text =
                    Editable.Factory.getInstance().newEditable("Support")
                binding.imageViewLine.setImageResource(R.drawable.support_icon)
            }

            else -> {
                binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Top")
                binding.imageViewLine.setImageResource(R.drawable.top_icon)
            }
        }
    }

//    companion object {
//        @JvmStatic
////        fun newInstance(param1: String, param2: String) =
//////            DetailsFragment().apply {
//////                arguments = Bundle().apply {}
//////            }
//    }
}