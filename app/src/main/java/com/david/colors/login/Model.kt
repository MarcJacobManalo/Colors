package com.david.colors.login

import android.util.Log.d
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import com.david.colors.model.api.RetrofitInit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class Model(private val presenter : Presenter): LoginMvp.Interactor {

    private var mCompositeDisposable : CompositeDisposable? = null

    override fun getLoginResponse(email:String,password:String) {
        val credentials = LogRegRequest(email,password)
        val retrofitInit = RetrofitInit.create().loginEmailPassword(credentials)
        mCompositeDisposable?.add(retrofitInit
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{
                    t1: Response<LogRegResponse>?, t2: Throwable? ->
                if (t1 != null) {
                    if (t1.isSuccessful) {
                        d("successful!",t1.body()?.token.toString())
                        presenter.view.onSuccessLogin()
                    }else{
                        d("failed!",t2?.localizedMessage.toString())
                        val error = t2?.localizedMessage.toString()
                        presenter.view.onFailedLogin(error)
                    }
                }
            })
    }


}