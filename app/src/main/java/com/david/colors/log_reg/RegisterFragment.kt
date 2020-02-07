package com.david.colors.log_reg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Log.d
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
import com.david.colors.model.Token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Response


class RegisterFragment : Fragment(){

    private var navController: NavController? = null
    private lateinit var pref: SharedPreferences
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mCompositeDisposable = CompositeDisposable()
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
            R.id.opSetting -> Toast.makeText(activity,"Setting!",Toast.LENGTH_SHORT).show() }
        return super.onOptionsItemSelected(item)
    }

    private fun postRequest() {
        val credentials = RegisterDataModels("eve.holt@reqres.in","pistol")
        val retrofitInit = RetrofitInit.create().registerEmailPassword(credentials)
        mCompositeDisposable?.add(retrofitInit
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { t: Response<Token>? ->
                if (t?.isSuccessful!!){
                    login(t.code())
                    d("successful!",t.code().toString()) }
                else d("failed!",t.code().toString())
            })
    }

    private fun login(responseCode: Int) {
        val submitBtn = view?.findViewById<Button>(R.id.btn_register_reg_frag)
        submitBtn?.setOnClickListener{

            val username = tv_user_reg_frag.text.toString().trim()
            val password = tv_pass_reg_frag.text.toString().trim()

            if(responseCode == 200){
                if (!(username.isEmpty() && password.isEmpty())) {
                    if(username == "eve.holt@reqres.in" && password == "pistol"){
                        pref = context!!.getSharedPreferences("user_details", Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("username",username)
                        editor.putString("password",password)
                        editor.apply()
                        navController?.navigate(R.id.action_registerFragment_to_colorList)
                        Toast.makeText(context,"Register Successful",Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(context,"Wrong email/password",Toast.LENGTH_SHORT).show()
                }else Toast.makeText(context,"Empty Field",Toast.LENGTH_SHORT).show()
            }else Toast.makeText(context,"Server not found",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
