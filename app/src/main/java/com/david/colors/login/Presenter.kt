package com.david.colors.login

 class Presenter(val view: LoginMvp.View): LoginMvp.Presenter{



     private val model = Model(this)

    override fun onLoginRequest(email:String,password:String) {
        model.getLoginResponse(email,password)
    }


}
