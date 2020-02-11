package com.david.colors.log_reg

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import android.util.Log.d
import android.view.inputmethod.InputMethodManager
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
     private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val username = tv_user.text.toString().trim()
        val password = tv_pass.text.toString().trim()
       when(v?.id) {
           R.id.btn_register -> mNavController?.navigate(R.id.action_loginFragment_to_registerFragment)
           R.id.btn_login -> postRequest(username,password)}
    }

    private fun postRequest(username:String,password:String) {
          closeKeyboard()
          val credentials = LogRegRequest(username,password)
          val retrofitInit = RetrofitInit.create().loginEmailPassword(credentials)
          mCompositeDisposable?.add(retrofitInit
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
              .subscribe{
                      t1: Response<LogRegResponse>?, t2: Throwable? ->
                  if (t1 != null) {
                      if (t1.isSuccessful) {
                          d("Token----",t1.body()?.token.toString())
                          login() }
                      else{
                          Toast.makeText(requireContext(),t1.body()?.error.toString(),Toast.LENGTH_SHORT).show()
                          d("Error----", t2?.message.toString())
                          }
                  }
              })
    }

    private fun login() {
            Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
            mNavController?.navigate(R.id.action_loginFragment_to_colorList)
    }

     fun closeKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }

}
