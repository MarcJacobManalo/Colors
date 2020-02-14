package com.david.colors.color_List_Details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.david.colors.R
import kotlinx.android.synthetic.main.fragment_color_details.*


open class ColorDetails : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = ColorDetailsArgs.fromBundle(it)
            tv_color.text = args.colorAttributes[0]
            tv_pantone.text = args.colorAttributes[1]
            tv_name.text = args.colorAttributes[2]
            fragment_bg.setBackgroundColor(Color.parseColor(tv_color.text.toString()))

            Log.d("hex", args.colorAttributes[0])
            Log.d("color", args.colorAttributes[1])
            Log.d("color", args.colorAttributes[2])
        }


    }
}
