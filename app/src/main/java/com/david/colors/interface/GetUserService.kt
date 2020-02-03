package com.david.colors.`interface`


import com.david.colors.model.ColorDataObjectModel
import com.david.colors.model.LoginDataModels
import com.david.colors.model.RegisterDataModels
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors(): Observable<ColorDataObjectModel>

    @POST("api/login")
    fun loginEmailPassword(@Body loginData: LoginDataModels): Call<LoginDataModels>

    @POST("api/register")
    fun registerEmailPassword(@Body registerData: RegisterDataModels): Call<RegisterDataModels>
}

