package com.david.colors.login

import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import com.david.colors.model.api.RetrofitInit
import io.reactivex.Single
import retrofit2.Response
class LoginModel: LoginMvp.Interactor {

     override fun getLoginResponse(loginRequest: LogRegRequest): Single<Response<LogRegResponse>> {
         return RetrofitInit.create().loginEmailPassword(loginRequest)
     }
}