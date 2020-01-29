package com.david.colors


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class ColorDetails : Fragment() {
    private lateinit var navController: NavController

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
        navController = Navigation.findNavController(view)

        val pref = activity!!.getSharedPreferences("SP",MODE_PRIVATE)

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
        inflater.inflate(R.menu.actionbar_items_layout, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opLogout ->  navController.navigate(R.id.action_colorDetails_to_loginFragment)
            R.id.opSetting -> Toast.makeText(activity, "Setting!", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)

    }
}
