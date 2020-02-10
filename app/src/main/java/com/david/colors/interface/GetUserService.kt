package com.david.colors.`interface`


import com.david.colors.model.ColorList
import com.david.colors.model.LogRegRequest
import com.david.colors.model.LogRegResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors():
            Single<Response<ColorList>>

    @POST("api/login")
    fun loginEmailPassword(@Body loginData: LogRegRequest):
            Single <Response <LogRegResponse>>

    @POST("api/register")
    fun registerEmailPassword(@Body registerData: LogRegRequest):
            Single<Response<LogRegResponse>>

}