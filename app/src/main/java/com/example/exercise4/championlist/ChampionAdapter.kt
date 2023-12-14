package com.example.exercise4.championlist

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise4.Lane
import com.example.exercise4.R
import com.example.exercise4.data.Champion
import com.example.exercise4.data.ChampionRepository

class ChampionAdapter(var data: MutableList<Champion>, var dataRepo: ChampionRepository) : RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {
    private val MAX_TEXT_LENGTH = 25
    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener) {
        this.onLongClickListener = onLongClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = data[position]

        when(ItemsViewModel.lane){
            Lane.TOP ->{
                holder.imageView.setImageResource(R.drawable.top_icon)
            }
            Lane.JUNGLE ->{
                holder.imageView.setImageResource(R.drawable.jungle_icon)
            }
            Lane.MID ->{
                holder.imageView.setImageResource(R.drawable.mid_icon)
            }
            Lane.BOTTOM ->{
                holder.imageView.setImageResource(R.drawable.adc_icon)
            }
            Lane.SUPPORT ->{
                holder.imageView.setImageResource(R.drawable.support_icon)
            }
        }

        holder.textView.text = ItemsViewModel.name
        if (ItemsViewModel.description.length < MAX_TEXT_LENGTH)
            holder.textView2.text = ItemsViewModel.description
        else
            holder.textView2.text = ItemsViewModel.description.removeRange(MAX_TEXT_LENGTH, ItemsViewModel.description.length) + "..."
        holder.itemView.setOnClickListener {
            if(onClickListener != null){
                onClickListener!!.onClick(position, ItemsViewModel)

//                val action = R.id.action_nav_recycler_view_db_to_championDetailFragment
//                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.setOnLongClickListener {
            if (dataRepo.deleteItem(data[position])) {
                data = dataRepo.getAllData()!!
                notifyDataSetChanged()
            }
            true
        }

        Log.d("ChampionAdapter", "ItemsViewModel.rating: ${ItemsViewModel.rating}")

        if (ItemsViewModel.rating > 2.5) {
            val greenColor = ContextCompat.getColor(holder.itemView.context, R.color.green)
            holder.itemView.setBackgroundColor(greenColor)
        } else {
            val redColor = ContextCompat.getColor(holder.itemView.context, R.color.red)
            holder.itemView.setBackgroundColor(redColor)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addChampion(champion: Champion){
        data.add(champion)
        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun deleteChampion(champion: Champion) {
        data.remove(champion)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(position: Int, champion: Champion)
    }

    interface OnLongClickListener {
        fun onLongClick(position: Int, champion: Champion)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
    }
}