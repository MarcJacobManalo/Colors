package com.david.colors.adapter
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.colors.R
import com.david.colors.api_retrofit.DataClassColorApi
import kotlinx.android.synthetic.main.list_colors.view.*

class CustomAdapter(private val username:List<DataClassColorApi>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_colors,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int{
        return username.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = username[position]
        holder.tvName.text = model.name

     /*   holder.itemView.setOnClickListener(View.OnClickListener {

        })*/

    }

    class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.colorsList

        /*    init {
                itemView.setOnClickListener {
                 d("try",itemView.id.toString())
                }
            }*/
    }
}

