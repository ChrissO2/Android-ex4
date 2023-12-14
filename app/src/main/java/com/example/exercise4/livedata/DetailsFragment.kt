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
        val name = bundle!!.getString("name", "Default name")
        val description = bundle!!.getString("description", "No description")
        val lane = bundle!!.getInt("lane", 0)
        val rating = bundle!!.getFloat("rating", 1.0F)


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

        binding.buttonReturn.setOnClickListener {
            if (hasChanged) {
                val name = binding.textViewName.text.toString()
                val description = binding.textViewDescription.text.toString()
                val lane = binding.textViewLine.text.toString()
                val rating = binding.textViewRating.text.toString()
                val bundle = Bundle()
                bundle.putString("name", name)
                bundle.putString("description", description)
                bundle.putInt("lane", lane.toInt())
                bundle.putFloat("rating", rating.toFloat())
                parentFragmentManager.setFragmentResult("item_updated", bundle)
                findNavController().navigateUp()
            }
            requireActivity().onBackPressed()
        }

        binding.buttonModify.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("description", description)
            bundle.putInt("lane", lane)
            bundle.putFloat("rating", rating)

            findNavController().navigate(
                R.id.action_detailsFragment_to_updateFragment,
                bundle
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}