package com.example.exercise4.tabs

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.exercise4.Tab1Fragment
import com.example.exercise4.Tab2Fragment
import com.example.exercise4.R

class ViewPager2(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(position)
    }
}