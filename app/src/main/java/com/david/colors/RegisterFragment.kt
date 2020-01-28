package com.david.colors


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(),View.OnClickListener {

    private lateinit var navController:NavController

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

    override fun onClick(v: View?) {
        val username = tv_user_reg_frag.text.toString()
        val password = tv_pass_reg_frag.text.toString()

        when {
            username.isEmpty() -> tv_user_reg_frag.error = "Please fill this form"
            username.length > 15 -> tv_user_reg_frag.error = "Email too long"
            password.isEmpty() -> tv_pass_reg_frag.error = "Please fill this form"
            else -> when (v!!.id) {
                R.id.btn_register_reg_frag-> navController.navigate(R.id.action_registerFragment_to_colorList)
            }
        }
    }
}
