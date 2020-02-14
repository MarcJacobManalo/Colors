package com.david.colors.login

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import android.widget.Toast
import com.david.colors.R
import io.reactivex.disposables.CompositeDisposable

class LoginFragment : Fragment(), View.OnClickListener, LoginMvp.View {

    private val presenter : Presenter by lazy { Presenter(this) }
    private var mNavController : NavController? = null
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this) }

    override fun onClick(v: View?) {
        val email = tv_user.text.toString().trim()
        val password = tv_pass.text.toString().trim()

        when(v?.id) {
            R.id.btn_register -> mNavController?.navigate(R.id.action_loginFragment_to_registerFragment)
            R.id.btn_login -> onLoginRequest(email,password)
        } }

    private fun onLoginRequest(email:String,password:String) {
        closeKeyboard()
        presenter.onLoginRequest(email,password) }

    override fun onSuccessLogin() {
        Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
        mNavController?.navigate(R.id.action_loginFragment_to_colorList) }

    override fun onFailedLogin(error:String) {
        Toast.makeText(requireContext(),error,Toast.LENGTH_SHORT).show() }

    private fun closeKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        } }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear() }

}
