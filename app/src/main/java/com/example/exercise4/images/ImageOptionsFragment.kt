package com.example.exercise4.images

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise4.R
import com.example.exercise4.databinding.FragmentImageOptionsBinding


class ImageOptionsFragment : Fragment() {
    private lateinit var binding: FragmentImageOptionsBinding
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: SharedPreferences = requireActivity().getSharedPreferences("image", 0)

        val photoRepository = ImageRepository.getinstance(requireContext())
        val images = photoRepository.getAppList()!!
        Log.d("OptionsFragment", "onViewCreated: $images")


        val currentPageIndex = arguments?.getInt("position")!!

        Log.d("OptionsFragment", "onViewCreated: $currentPageIndex")

        imageViewPagerAdapter = ImageViewPagerAdapter(images)

        setUpViewPager(images, currentPageIndex)

        binding.viewPager.setCurrentItem(currentPageIndex, false)
    }

    private fun setUpViewPager(images: MutableList<ImageItem>, currentPageIndex: Int = 1) {

        Log.d("OptionsFragment", "setUpViewPager: $currentPageIndex")

        binding.viewPager.adapter = imageViewPagerAdapter

//        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

//        binding.viewPager.registerOnPageChangeCallback(
//            object : ViewPager2.OnPageChangeCallback() {
//
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    val data: SharedPreferences = requireActivity().getSharedPreferences("image", 0)
//                    val editor: SharedPreferences.Editor = data.edit()
//                    editor.putString("image", images[position].path)
//                    editor.commit()
//                }
//            }
//        )

        binding.buttonSetImage.setOnClickListener {
            val data: SharedPreferences = requireActivity().getSharedPreferences("image", 0)
            val editor: SharedPreferences.Editor = data.edit()
            editor.putString("image", images[binding.viewPager.currentItem].path)
            editor.commit()
            Toast.makeText(requireContext(), "Image has been set", Toast.LENGTH_LONG).show()
        }
    }
}