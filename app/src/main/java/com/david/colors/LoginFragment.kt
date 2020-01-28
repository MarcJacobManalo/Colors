package com.david.colors

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(),View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_login).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_register).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar_items_layout,menu)

        for (i in 0 until menu.size())
            menu.getItem(i).isVisible = false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opSetting -> Toast.makeText(activity,"Setting!",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        val username = tv_user.text.toString().trim()
        val password = tv_pass.text.toString().trim()

        when(v.id) {
            R.id.btn_register-> navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        when {
            username.isEmpty() -> tv_user.error = "Please fill this form"
            username.length > 15 -> tv_user.error = "Email too long"
            password.isEmpty() -> tv_pass.error = "Please fill this form"
            username == ("admin") && password == ("123") ->

                when(v.id) {

                    R.id.btn_login-> navController.navigate(R.id.action_loginFragment_to_colorList)

                }

            else->{
                Toast.makeText(context,"Wrong Password/Email!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}