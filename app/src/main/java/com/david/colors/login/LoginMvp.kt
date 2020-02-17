package com.david.colors.login

import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.Single
import retrofit2.Response

class LoginMvp {

    interface View{
        fun onSuccessLogin()
        fun onFailedLogin(error:String)
    }

    interface Presenter{
        fun onLoginRequest(email:String,password:String)
        fun clearDisposables()
    }

    interface Interactor{
        fun getLoginResponse(loginRequest: LogRegRequest): Single<Response<LogRegResponse>>
    }
}