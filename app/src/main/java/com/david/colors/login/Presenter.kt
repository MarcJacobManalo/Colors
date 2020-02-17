package com.david.colors.login

 class Presenter( view: LoginMvp.View): LoginMvp.Presenter{

     private val model = Model(view)

    override fun onLoginRequest(email:String,password:String) {
        model.getLoginResponse(email,password)
    }
}
