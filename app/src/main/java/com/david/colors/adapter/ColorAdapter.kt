package com.david.colors.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.colors.R
import com.david.colors.model.Color
import kotlinx.android.synthetic.main.list_colors.view.*

class ColorAdapter(private val colorList : List<Color> ,private var clickListener : OnClickItemsColor): RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_colors,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount() = colorList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(colorList[position],clickListener)
    }

    class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
         private val tvName: TextView = itemView.colorsList

        fun initialize(item: Color, action:OnClickItemsColor){
            tvName.text = item.name
            itemView.setOnClickListener {
                action.onItemClicked(item,adapterPosition)
            }
        }
    }
}

interface OnClickItemsColor{
    fun onItemClicked(item : Color, position : Int)
}