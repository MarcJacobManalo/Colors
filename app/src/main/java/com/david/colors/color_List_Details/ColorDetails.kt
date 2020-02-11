package com.david.colors.color_List_Details


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
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
        pref = requireContext().getSharedPreferences("ColorAttributes",MODE_PRIVATE)

        tv_color.text =  pref.getString("COLOR","def").toString()
        tv_name.text = pref.getString("NAME","def").toString()
        tv_pantone.text = pref.getString("PANTONE","def").toString()
        val bgColor = tv_color.text.toString()
        fragment_bg.setBackgroundColor(Color.parseColor(bgColor))
    }
}
