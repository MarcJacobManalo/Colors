package com.david.colors.login

import com.david.colors.model.LogRegRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException

class Presenter(private val view: LoginMvp.View) : LoginMvp.Presenter {

    private val model = LoginModel()
    private val mCompositeDisposable: CompositeDisposable? = null

    override fun onLoginRequest(email: String, password: String) {
       val subscription = model.getLoginResponse(LogRegRequest(email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               //do anything with LogRegResponse
               view.onSuccessLogin()
           },{
               view.onFailedLogin(it.message ?: throw IllegalArgumentException("No error message"))
           })
        mCompositeDisposable?.add(subscription)
    }

    override fun clearDisposables() {
        mCompositeDisposable?.clear()
    }

}
