package com.example.exercise4

import CustomAdapter
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.example.exercise4.data.ChampionRepository
import com.example.exercise4.databinding.FragmentListChampionBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListChampionFragment : Fragment() {
    // nowe
    lateinit var dataRepo : ChampionRepository
    lateinit var adapter : ChampionAdapter
    // stare
    private var columnCount = 1
    private lateinit var binding: FragmentListChampionBinding
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataRepo = ChampionRepository(requireContext())
        adapter = ChampionAdapter(dataRepo.getAllData()!!, dataRepo)

//        parentFragmentManager.setFragmentResultListener("item_added", this){
//            requestKey, _ ->
//                adapter.data = dataRepo.getAllData()!!
//                adapter.notifyDataSetChanged()
//        }

        parentFragmentManager.setFragmentResultListener("item_added", this) { _, _ ->
            GlobalScope.launch(Dispatchers.Main) {
                adapter.data = (dataRepo.getAllData() ?: emptyList()).toMutableList()
                adapter.notifyDataSetChanged()
            }
        }

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

//    fun addChampion(champion: ChampionModel) {
//        val adapter = recyclerview.adapter as CustomAdapter
//        adapter.addChampion(champion)
//    }
//
//    fun deleteChampion(champion: ChampionModel) {
//        val adapter = recyclerview.adapter as CustomAdapter
//        adapter.deleteChampion(champion)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListChampionBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_list_champion, container, false)

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
//        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1) {
//            val champion = data?.getSerializableExtra("champion") as ChampionModel
//            addChampion(champion)
//        }
//        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 2) {
//            val champion = data?.getSerializableExtra("champion") as? ChampionModel
//            champion?.let {
//                deleteChampion(it)
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val recView = view.findViewById<RecyclerView>(R.id.recyclerviewdb)
//        recView.layoutManager = LinearLayoutManager(requireContext())
//        recView.adapter = adapter
        recyclerview = view.findViewById(R.id.recyclerviewdb)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = adapter

        var myBinding = FragmentListChampionBinding.bind(view)

        myBinding.addChampionBtn.setOnClickListener {
            // Use NavController to navigate to the ChampionAddDbFragment
            findNavController().navigate(R.id.action_listChampionFragment_to_championAddDbFragment)
        }


    }

    private fun showDeleteConfirmationDialog(champion: ChampionModel) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete Champion")
        alertDialog.setMessage("Are you sure you want to delete ${champion.name}?")

        alertDialog.setPositiveButton("Yes") { _, _ ->
//            deleteChampion(champion)
        }

        alertDialog.setNegativeButton("No") { _, _ ->
        }

        alertDialog.show()
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
