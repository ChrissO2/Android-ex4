package com.example.exercise4.livedata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise4.Lane
import com.example.exercise4.R
import com.example.exercise4.data.Champion
import com.example.exercise4.databinding.FragmentList4Binding
import com.example.exercise4.databinding.CardViewDesignBinding
import android.widget.AdapterView.OnItemLongClickListener


class List4Fragment : Fragment() {

    private lateinit var myViewModel: MyViewModel
    private lateinit var adapter: MyAdapter
    private lateinit var _binding: FragmentList4Binding
    private var selectedItemIndex: Int = RecyclerView.NO_POSITION


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentList4Binding.inflate(inflater, container, false)

        val recView = _binding.recyclerview
        recView.layoutManager = LinearLayoutManager(requireContext())
        val repository = ListRepository.getInstance(requireContext())
        myViewModel = MyViewModel(repository)
        adapter = MyAdapter(myViewModel.items)
        recView.adapter = adapter

        myViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            adapter.notifyDataSetChanged()
        })

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("addNewItem", viewLifecycleOwner) { _, bundle ->
            if (bundle.getBoolean("toAdd")) {
                val itemName = bundle.getString("name", "New person")
                val itemDescription = bundle.getString("description", "Some spec")
                val itemLane = bundle.getInt("lane", 0)
                val itemRating = bundle.getFloat("rating", 1.0F)
                val newItem = Champion(itemName, itemDescription, itemLane, itemRating)
                myViewModel.addChampion(newItem)
            }
        }

        parentFragmentManager.setFragmentResultListener("editItem", viewLifecycleOwner) { _, bundle ->
            if (bundle.getBoolean("edit")) {
                val itemName = bundle.getString("name", "Default name")
                val itemDescription = bundle.getString("description", "No description")
                val itemLane = bundle.getInt("lane", 0)
                val itemRating = bundle.getFloat("rating", 1.0F)
                val newItem = Champion(itemName, itemDescription, itemLane, itemRating)
                val pos = bundle.getInt("position")
                myViewModel.updateChampion(pos, itemName, itemDescription, itemLane, itemRating)
            }
        }

        setHasOptionsMenu(true)
    }


    inner class MyAdapter(var data: LiveData<List<Champion>>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private var listener: AdapterView.OnItemClickListener? = null

        inner class MyViewHolder(viewBinding: CardViewDesignBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val imageView: ImageView = viewBinding.imageview
            val textView: TextView = viewBinding.textView
            val textView2: TextView = viewBinding.textView2
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = CardViewDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.value?.size ?: 0
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val champion = data.value?.get(position) ?: return

            when(champion.lane){
                Lane.TOP->{
                    holder.imageView.setImageResource(R.drawable.top_icon)
                }
                Lane.JUNGLE->{
                    holder.imageView.setImageResource(R.drawable.jungle_icon)
                }
                Lane.MID->{
                    holder.imageView.setImageResource(R.drawable.mid_icon)
                }
                Lane.BOTTOM->{
                    holder.imageView.setImageResource(R.drawable.adc_icon)
                }
                Lane.SUPPORT->{
                    holder.imageView.setImageResource(R.drawable.support_icon)
                }
            }

            holder.textView.text = champion.name
            holder.textView2.text = champion.description

            holder.itemView.setOnClickListener {
            }

            holder.itemView.setOnLongClickListener {
                myViewModel.deleteChampion(champion)
                true
            }

            val backgroundColor = if (champion.rating <= 2.5) {
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            }

            holder.itemView.setBackgroundColor(backgroundColor)

        }
    }
}
