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
import com.david.colors.`interface`.RetrofitInit
import com.david.colors.model.LoginDataModels
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Response


class LoginFragment : Fragment(),View.OnClickListener {


     private var navController: NavController? = null
     private lateinit var pref: SharedPreferences
    private var mCompositeDisposable : CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mCompositeDisposable = CompositeDisposable()
        postRequest()
    }

    override fun onResume() {
         super.onResume()
         session()
     }

     private fun session() {
         pref = context!!.getSharedPreferences("user_details", MODE_PRIVATE)
         if (pref.contains("username")) {
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
       when(v.id) {
           R.id.btn_register -> navController?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun postRequest() {

        val credentials = LoginDataModels("eve.holt@reqres.in","cityslicka")

        val retrofitInit = RetrofitInit.create().loginEmailPassword(credentials)

       retrofitInit.enqueue(object : retrofit2.Callback<LoginDataModels> {

            override fun onResponse(call: Call<LoginDataModels>, response: Response<LoginDataModels>) {
                val code = response.code()
                d("onResponse",code.toString())
                login(code)
            }
            override fun onFailure(call: Call<LoginDataModels>, t: Throwable) {
                d("onFailure",t.message.toString())
            }
        })

    }
    private fun login(code:Int) {

        val submitBtn = view?.findViewById<Button>(R.id.btn_login)
        submitBtn?.setOnClickListener{

            val username = tv_user.text.toString().trim()
            val password = tv_pass.text.toString().trim()
            if(code == 200){
                if (!(username.isEmpty() && password.isEmpty())) {
                    if(username == "eve.holt@reqres.in" && password == "cityslicka"){
                        val editor = pref.edit()
                        editor.putString("username",username)
                        editor.putString("password",password)
                        editor.apply()
                        navController?.navigate(R.id.action_loginFragment_to_colorList)
                        Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()

                    }else Toast.makeText(context,"Wrong email/password",Toast.LENGTH_SHORT).show()

                }else Toast.makeText(context,"Empty Field",Toast.LENGTH_SHORT).show()
            }else Toast.makeText(context,"Sever not found",Toast.LENGTH_SHORT).show()
        }
    }
}
