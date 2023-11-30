package com.example.exercise4

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.exercise4.databinding.FragmentRecyclerViewListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {

    private var columnCount = 1
    lateinit var list1 : RecyclerView
    private lateinit var binding: FragmentRecyclerViewListBinding
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    fun addChampion(champion: ChampionModel) {
        val adapter = recyclerview.adapter as CustomAdapter
        adapter.addChampion(champion)
    }

    fun deleteChampion(champion: ChampionModel) {
        val adapter = recyclerview.adapter as CustomAdapter
        adapter.deleteChampion(champion)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerViewListBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_recycler_view_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1) {
            val champion = data?.getSerializableExtra("champion") as ChampionModel
            addChampion(champion)
        }
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 2) {
//            val champion = data?.getSerializableExtra("champion") as ChampionModel
//            deleteChampion(champion)
            val champion = data?.getSerializableExtra("champion") as? ChampionModel
            champion?.let {
                deleteChampion(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview = view.findViewById(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val data = Constants.getChampionsList()

        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter

        adapter.setOnClickListener(object : CustomAdapter.OnClickListener {
            override fun onClick(position: Int, champion: ChampionModel) {
                val intent = Intent(requireContext(), ItemDetails::class.java)
                intent.putExtra(NEXT_SCREEN, champion)
                startActivityForResult(intent, 2)
            }
        })
        val button = view.findViewById<FloatingActionButton>(R.id.addChampionActionButton)
        button.setOnClickListener {
            val intent = Intent(requireContext(), ChampionAddActivity::class.java)
            startActivityForResult(intent, 1)
        }
}

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        val NEXT_SCREEN="details_screen"
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}