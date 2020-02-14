package com.david.colors.register

import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.david.colors.R
import com.david.colors.login.LoginFragment
import com.david.colors.model.api.RetrofitInit
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Response


class RegisterFragment : Fragment(),View.OnClickListener{

    private var mNavController: NavController? = null
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        btn_navLogin.setOnClickListener(this)
        btn_register_reg_frag.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val username = tv_user_reg_frag.text.toString().trim()
        val password = tv_pass_reg_frag.text.toString().trim()

        when(v?.id){
            R.id.btn_navLogin -> mNavController?.navigate(R.id.action_registerFragment_to_loginFragment)
            R.id.btn_register_reg_frag -> postRequest(username,password)
        }
    }

    private fun postRequest(username:String,password:String) {
            //closeKeyBoard()
            val credentials = LogRegRequest(username,password)
            val retrofitInit = RetrofitInit.create().registerEmailPassword(credentials)
        mCompositeDisposable?.add(retrofitInit
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{
                    t1: Response<LogRegResponse>?, t2: Throwable? ->
                if (t1 != null) {
                    if (t1.isSuccessful) {
                        register()
                        d("Register Successful", t1.code().toString()) }
                    else{
                        Toast.makeText(requireContext(),"Register Failed",Toast.LENGTH_SHORT).show()
                        d("error", t2?.message.toString())}
                }
            })
    }

    private fun register() {
        Toast.makeText(requireContext(),"Register Successful",Toast.LENGTH_SHORT).show()
        mNavController?.navigate(R.id.action_registerFragment_to_colorList)

    }

//    private fun closeKeyBoard(){
//        val keyboard = LoginFragment()
//        keyboard.closeKeyboard()
//        let {
//            return@let keyboard
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
