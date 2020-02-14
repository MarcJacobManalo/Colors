package com.david.colors.login

class LoginMvp {

    interface View{
        fun onSuccessLogin()
        fun onFailedLogin(error:String)
    }

    interface Presenter{
        fun onLoginRequest(email:String,password:String)
    }

    interface Interactor{
        fun getLoginResponse(email:String,password:String)
    }
}