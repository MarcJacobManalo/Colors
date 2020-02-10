package com.david.colors.log_reg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.david.colors.R
import com.david.colors.`interface`.RetrofitInit
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Response


class RegisterFragment : Fragment(),View.OnClickListener{


    private var mNavController: NavController? = null
    private lateinit var mPref: SharedPreferences
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
        postRequest()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        btn_navLogin.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_navLogin -> mNavController?.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun postRequest() {
        val credentials = LogRegRequest("eve.holt@reqres.in","pistol")
        val retrofitInit = RetrofitInit.create().registerEmailPassword(credentials)
        mCompositeDisposable?.add(retrofitInit
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{
                    t1: Response<LogRegResponse>?, t2: Throwable? ->

                if (t1 != null) {
                    if (t1.isSuccessful) {
                        d("success", t1.code().toString())
                        register()}
                    else{
                        Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT).show()
                        d("error", t2?.message.toString())}
                }

            }
        )
    }

    private fun register() {
        btn_register_reg_frag?.setOnClickListener{
            val username = tv_user_reg_frag.text.toString().trim()
            val password = tv_pass_reg_frag.text.toString().trim()

                if (!(username.isEmpty() && password.isEmpty())) {
                    if(username == "eve.holt@reqres.in" && password == "pistol"){
                        mPref = requireContext().getSharedPreferences("user_details", Context.MODE_PRIVATE)
                        val editor = mPref.edit()
                        editor.putString("username",username)
                        editor.putString("password",password)
                        editor.apply()
                        mNavController?.navigate(R.id.action_registerFragment_to_colorList)
                        Toast.makeText(requireContext(),"Register Successful",Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(requireContext(),"Wrong email/password",Toast.LENGTH_SHORT).show()
                }else Toast.makeText(requireContext(),"Empty Field",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
