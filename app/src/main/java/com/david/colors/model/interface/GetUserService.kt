package com.david.colors.model.`interface`


import com.david.colors.model.ColorList
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface GetUserService {

    @GET("Api/unknown")
    fun getAllColors():
            Single<Response<ColorList>>

    @POST("Api/login")
    fun loginEmailPassword(@Body loginData: LogRegRequest):
            Single <Response <LogRegResponse>>

    @POST("Api/register")
    fun registerEmailPassword(@Body registerData: LogRegRequest):
            Single<Response<LogRegResponse>>

}