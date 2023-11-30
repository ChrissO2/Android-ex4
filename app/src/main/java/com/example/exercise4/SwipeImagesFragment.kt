package com.example.exercise4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise4.databinding.FragmentSwipeImagesBinding

class SwipeImagesFragment : Fragment() {

    private var images = intArrayOf(R.drawable.lee_sin) // reszta po przecinku
    private lateinit var binding: FragmentSwipeImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentSwipeImagesBinding.inflate(layoutInflater)
        val viewPager = binding.viewPagerMain
        super.onCreate(savedInstanceState)
        val adapter = ImagePagerAdapter()
        adapter.ViewPagerAdapter(requireContext(), images)
        viewPager.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe_images, container, false)
    }

}