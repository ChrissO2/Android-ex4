package com.example.exercise4

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentDetailRealBinding


class DetailRealFragment : Fragment() {
    private lateinit var binding: FragmentDetailRealBinding
    private lateinit var dataRepo: ChampionRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailRealBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRepo = ChampionRepository.getInstance(requireContext())!!

        val championId = arguments?.getInt("id")
        if (championId != null) {
            val champion = dataRepo.getItemById(championId)
            val name = champion?.name
            val description = champion?.description
            val lane = champion?.lane
            val rating = champion?.rating

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
                    binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Support")
                    binding.imageViewLine.setImageResource(R.drawable.support_icon)
                }
                else -> {
                    binding.textViewLine.text = Editable.Factory.getInstance().newEditable("Top")
                    binding.imageViewLine.setImageResource(R.drawable.top_icon)
                }
            }
        }

        binding.buttonReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.buttonModify.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", championId!!)
            findNavController().navigate(R.id.action_detailRealFragment_to_championDetailFragment, bundle)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailRealFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}