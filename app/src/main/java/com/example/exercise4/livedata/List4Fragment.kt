package com.example.exercise4.livedata

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import com.example.exercise4.championlist.ChampionAdapter
import com.example.exercise4.championlist.ListChampionFragment
import com.example.exercise4.data.ChampionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        adapter = MyAdapter(myViewModel.items, myViewModel)
        recView.adapter = adapter

        myViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            adapter.notifyDataSetChanged()
        })

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("item_added", viewLifecycleOwner) { _, bundle ->
            val itemName = bundle.getString("name", "New person")
            val itemDescription = bundle.getString("description", "Some spec")
            val itemLane = bundle.getInt("lane", 0)
            val itemRating = bundle.getFloat("rating", 1.0F)
            val newItem = Champion(itemName, itemDescription, itemLane, itemRating)
            myViewModel.addChampion(newItem)
        }

        parentFragmentManager.setFragmentResultListener("item_updated", viewLifecycleOwner) { _, bundle ->
            Log.d("List4Fragment", "onViewCreated item_updated: $bundle")
            val itemName = bundle.getString("name", "Default name")
            val itemDescription = bundle.getString("description", "No description")
            val itemLane = bundle.getInt("lane", 0)
            val itemRating = bundle.getFloat("rating", 1.0F)
            val position = bundle.getInt("position", 0)
            Log.d("List4Fragment", "onViewCreated item_updated: $position")
            myViewModel.updateChampion(position, itemName, itemDescription, itemLane, itemRating)
        }

        _binding.addChampionActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_list_view_db_to_addFragment)
        }

        setHasOptionsMenu(true)
    }


    inner class MyAdapter(var data: LiveData<List<Champion>>, private val myViewModel: MyViewModel) :
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
            val position = holder.getAdapterPosition()
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
                selectedItemIndex = position
                val bundle = Bundle()
                bundle.putString("name", champion.name)
                bundle.putString("description", champion.description)
                bundle.putInt("lane", champion.lane)
                bundle.putFloat("rating", champion.rating)
                bundle.putInt("id", champion.id)
                bundle.putInt("position", position)

                findNavController().navigate(R.id.action_nav_list_view_db_to_detailsFragment, bundle)
            }

            holder.itemView.setOnLongClickListener {
                myViewModel.deleteChampion(champion)
                true
            }

            val backgroundColor = if (champion.rating > 2.5) {
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            }

            holder.itemView.setBackgroundColor(backgroundColor)

        }
    }
}
