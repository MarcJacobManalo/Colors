package com.david.colors.recyclerview


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.david.colors.R
import kotlinx.android.synthetic.main.fragment_color_details.*


class ColorDetails : Fragment() {

    private lateinit var pref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = requireContext().getSharedPreferences("SP",MODE_PRIVATE)
        val colorName = pref.getString("NAME","def").toString()
        val colorPan = pref.getString("PANTONE","def").toString()
        val colorCol = pref.getString("COLOR","def").toString()

        val checkColorDetails = "Name: $colorName"+"Pantone: $colorPan"+"Color: $colorCol"
        Log.d("n", checkColorDetails)

        tv_color.text = colorCol
        tv_name.text = colorName
        tv_pantone.text = colorPan
        fragment_bg.setBackgroundColor(Color.parseColor(colorCol))
    }
}
