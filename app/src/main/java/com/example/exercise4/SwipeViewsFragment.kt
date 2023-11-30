package com.example.exercise4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise4.databinding.FragmentSwipeViewsBinding
import com.google.android.material.tabs.TabLayoutMediator

class SwipeViewsFragment : Fragment() {

    private var _binding: FragmentSwipeViewsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeViewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        val tabLM = TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Tab 1"
                1 -> tab.text = "Tab 2"
            }
        }
        tabLM.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}