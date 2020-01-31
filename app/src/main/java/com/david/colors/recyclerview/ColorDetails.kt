package com.david.colors.recyclerview


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.david.colors.R


class ColorDetails : Fragment() {

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_color_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = activity!!.getSharedPreferences("SP",MODE_PRIVATE)

        val cName = pref.getString("NAME","def").toString()
        val cPan = pref.getString("PANTONE","def").toString()
        val cCol = pref.getString("COLOR","def").toString()

        Log.d("n", cName)
        Log.d("p", cPan)
        Log.d("c", cCol)

        val colorD = view.findViewById<TextView>(R.id.color_d)
        val nameD = view.findViewById<TextView>(R.id.name_d)
        val pantoneD = view.findViewById<TextView>(R.id.pantone_d)

        colorD.text = cCol
        nameD.text = cName
        pantoneD.text = cPan
    }
   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)

    }
}
