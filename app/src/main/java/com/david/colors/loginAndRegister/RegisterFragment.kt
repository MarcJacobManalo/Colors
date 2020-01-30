package com.david.colors.loginAndRegister


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.david.colors.R
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(),View.OnClickListener {


    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_register_reg_frag).setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true) //Set Up Button RF to LF
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar_items_layout,menu) // Use the actionbar_items_layout for the actionbar
        menu.getItem(1).isVisible = false //hide logout item in RegisterFragment

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opSetting -> Toast.makeText(activity,"Setting!",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        val username = tv_user_reg_frag.text.toString()
        val password = tv_pass_reg_frag.text.toString()

        if(v.id == R.id.btn_register_reg_frag){
            if (!(username.trim().isEmpty() || password.trim().isEmpty())) {

                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navController?.navigate(R.id.action_registerFragment_to_colorList)

            } else {
                Toast.makeText(context,"Enter credentials",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
