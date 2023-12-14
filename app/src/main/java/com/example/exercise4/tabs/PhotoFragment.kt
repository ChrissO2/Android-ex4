package com.example.exercise4.tabs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise4.R

private const val ARG_POSITION = "position"
private const val PREF_NAME = "MyPrefs"

class PhotoFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonSetImg: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        buttonSetImg = view.findViewById<Button>(R.id.buttonSetImg)
        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)

        val imageList = listOf(
            R.drawable.lee_sin,
            R.drawable.lol2,
            R.drawable.lol3,
        )

        val adapter = ImageAdapter(imageList)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                savePositionToSharedPreferences(position)
            }
        })


        buttonSetImg.setOnClickListener {
            val currentPosition = viewPager.currentItem
            savePositionToSharedPreferences(currentPosition)
        }

        return view
    }


    private fun savePositionToSharedPreferences(currentPosition: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("img", currentPosition)
        editor.apply()
    }

    companion object {

        @JvmStatic
        fun newInstance(position: Int) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}
