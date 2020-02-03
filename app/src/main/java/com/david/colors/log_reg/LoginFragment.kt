package com.david.colors.log_reg

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Context.MODE_PRIVATE
import android.util.Log.d
import com.david.colors.R

 class LoginFragment : Fragment(),View.OnClickListener {


     var navController: NavController? = null
     private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

     override fun onResume() {
         super.onResume()

         pref = context!!.getSharedPreferences("user_details", MODE_PRIVATE)
         if (pref.contains("username")) {
              navController?.navigate(R.id.action_loginFragment_to_colorList)
         }
         else if (pref.contains("123")) {
             navController?.navigate(R.id.action_loginFragment_to_colorList)
         }

         d("tag",pref.contains("username").toString())
         d("tag",pref.contains("password").toString())

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
            R.id.opSetting -> Toast.makeText(context,"Setting!",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        val username = tv_user.text.toString().trim()
        val password = tv_pass.text.toString().trim()


        if(v.id == R.id.btn_login){
            if (!(username.trim().isEmpty() || password.trim().isEmpty())) {
                if (username == "admin" && password == "123") {
                    val editor = pref.edit()
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.apply()
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navController?.navigate(R.id.action_loginFragment_to_colorList)
                } else {
                    Toast.makeText(context,"Login Failed",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context,"Enter credentials",Toast.LENGTH_SHORT).show()
            }

        }else if (v.id == R.id.btn_register) {
            navController?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}