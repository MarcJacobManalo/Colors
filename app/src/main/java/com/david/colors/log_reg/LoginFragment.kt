package com.david.colors.log_reg

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Context.MODE_PRIVATE
import android.util.Log.d
import com.david.colors.R
import com.david.colors.`interface`.RetrofitInit
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginFragment : Fragment(),View.OnClickListener {

     private var mNavController: NavController? = null
     private lateinit var mPref: SharedPreferences
     private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun onResume() {
         super.onResume()
         session()
         postRequest()
     }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v?.id) {
           R.id.btn_register -> mNavController?.navigate(R.id.action_loginFragment_to_registerFragment) }
    }

    private fun postRequest() {
        val credentials = LogRegRequest("eve.holt@reqres.in","cityslicka") // should put the edittext here
        val retrofitInit = RetrofitInit.create().loginEmailPassword(credentials)
        mCompositeDisposable?.add(retrofitInit
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{
                    t1: Response<LogRegResponse>?, t2: Throwable? ->

                if (t1 != null) {
                    if (t1.isSuccessful) {
                        login()
                        d("successful", t1.code().toString()) }
                    else{
                        Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT).show()
                        d("error", t2?.message.toString()) }
                }

            }
        )
    }
    /**
     * TODO:
     *
     * Should remove the validation
     * because the Api will handle it.
     *
     * Do this as well in register fragment.
     *
     * make a session/shareprefs as boolean.
     * **/
    private fun login() {
        btn_login?.setOnClickListener{
            val username = tv_user.text.toString().trim()
            val password = tv_pass.text.toString().trim()

                if (!(username.isEmpty() && password.isEmpty())) {
                    if(username == "eve.holt@reqres.in" && password == "cityslicka"){
                        val editor = mPref.edit()
                        editor.putString("username",username)
                        editor.putString("password",password)
                        editor.apply()
                        mNavController?.navigate(R.id.action_loginFragment_to_colorList)
                        Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(requireContext(),"Wrong email/password",Toast.LENGTH_SHORT).show()
                }else Toast.makeText(requireContext(),"Empty Field",Toast.LENGTH_SHORT).show()
        }
    }

    private fun session() {
        mPref = requireContext().getSharedPreferences("user_details", MODE_PRIVATE)
        if (mPref.contains("username")) {
            mNavController?.navigate(R.id.action_loginFragment_to_colorList) }

        d("SaveSessionUsername",mPref.contains("username").toString())
        d("SaveSessionPassword",mPref.contains("password").toString())

    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }
}
