package com.david.colors.log_reg

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
import com.david.colors.`interface`.RetrofitInit
import com.david.colors.model.RegisterDataModels
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Response


class RegisterFragment : Fragment(){


    private var navController: NavController? = null
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        postRequest()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

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

    private fun postRequest() {
        val credentials = RegisterDataModels("eve.holt@reqres.in","pistol")

        val retrofit = RetrofitInit.create().registerEmailPassword(credentials)

        retrofit.enqueue(object : retrofit2.Callback<RegisterDataModels> {
            override fun onResponse(call: Call<RegisterDataModels>, response: Response<RegisterDataModels>) {
                val code = response.code()
                Log.d("onResponse", code.toString())
                login(code)
            }

            override fun onFailure(call: Call<RegisterDataModels>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    private fun login(code: Int) {

        val submitBtn = view?.findViewById<Button>(R.id.btn_register_reg_frag)
        submitBtn?.setOnClickListener{

            val username = tv_user_reg_frag.text.toString().trim()
            val password = tv_pass_reg_frag.text.toString().trim()

            if(code == 200){
                if (!(username.isEmpty() && password.isEmpty())) {
                    if(username == "eve.holt@reqres.in" && password == "pistol"){
                        pref = context!!.getSharedPreferences("user_details", Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("username",username)
                        editor.putString("password",password)
                        editor.apply()
                        navController?.navigate(R.id.action_registerFragment_to_colorList)
                        Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()

                    }else Toast.makeText(context,"Wrong email/password",Toast.LENGTH_SHORT).show()

                }else Toast.makeText(context,"Empty Field",Toast.LENGTH_SHORT).show()

            }else Toast.makeText(context,"Server not found",Toast.LENGTH_SHORT).show()


        }


    }
    /*override fun onClick(v: View) {
        val username = tv_user_reg_frag.text.toString()
        val password = tv_pass_reg_frag.text.toString()
        pref = context!!.getSharedPreferences("user_details", Context.MODE_PRIVATE)
        val editor = pref.edit()

        if(v.id == R.id.btn_register_reg_frag){
            if (!(username.trim().isEmpty() || password.trim().isEmpty())) {
                   if(username == "123" && password == "123"){

                       editor.putString("username", username)
                       editor.putString("password", password)
                       editor.apply()

                       Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                       navController?.navigate(R.id.action_registerFragment_to_colorList)

                   }else{
                       Toast.makeText(context, "Wrong email/password", Toast.LENGTH_SHORT).show()
                   }

            } else {
                Toast.makeText(context,"Enter credentials",Toast.LENGTH_SHORT).show()
            }
        }

    }*/
}
