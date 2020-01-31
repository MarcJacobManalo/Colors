package com.david.colors.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.colors.R
import com.david.colors.api_retrofit.DataClassColorApi
import kotlinx.android.synthetic.main.list_colors.view.*

class CustomAdapter(private val username:List<DataClassColorApi>,  var clicklistener : OnClickItemsColor): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_colors,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int{
        return username.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.initialize(username[position],clicklistener)

    }

    class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
         val tvName: TextView = itemView.colorsList

        fun initialize(item: DataClassColorApi, action:OnClickItemsColor){
            tvName.text = item.name
            itemView.setOnClickListener {
                action.onItemClicked(item,adapterPosition)

            }
        }
    }
}

interface OnClickItemsColor{

    fun onItemClicked(item : DataClassColorApi, position : Int)

}