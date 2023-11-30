import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise4.ChampionModel
import com.example.exercise4.Lane
import com.example.exercise4.R


class CustomAdapter(private val mList: MutableList<ChampionModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val MAX_TEXT_LENGTH = 25
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        when(ItemsViewModel.lane){
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

        holder.textView.text = ItemsViewModel.name
        if (ItemsViewModel.description.length < MAX_TEXT_LENGTH)
            holder.textView2.text = ItemsViewModel.description
        else
            holder.textView2.text = ItemsViewModel.description.removeRange(MAX_TEXT_LENGTH, ItemsViewModel.description.length) + "..."
        holder.itemView.setOnClickListener {
            if(onClickListener != null){
                onClickListener!!.onClick(position, ItemsViewModel)
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun addChampion(champion: ChampionModel){
        mList.add(champion)
        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun deleteChampion(champion: ChampionModel) {
        mList.remove(champion)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(position: Int, champion: ChampionModel)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
    }
}