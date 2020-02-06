package com.david.colors.`interface`


import com.david.colors.model.ColorDataObjectModel
import com.david.colors.model.LoginDataModels
import com.david.colors.model.RegisterDataModels
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors(): Observable<ColorDataObjectModel>

    @POST("api/login")
    fun loginEmailPassword(@Body loginData: LoginDataModels): Single <Response <LoginDataModels>>

    @POST("api/register")
    fun registerEmailPassword(@Body registerData: RegisterDataModels): Call<RegisterDataModels>


}