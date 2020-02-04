package com.david.colors.`interface`


import com.david.colors.model.ColorDataObjectModel
import com.david.colors.model.LoginDataModels
import com.david.colors.model.RegisterDataModels
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors(): Observable<ColorDataObjectModel>

    @FormUrlEncoded
    @POST("api/login")
    fun loginEmailPassword(@Body loginData: LoginDataModels): Call <LoginDataModels>

    @POST("api/register")
    fun registerEmailPassword(@Body registerData: RegisterDataModels): Call<RegisterDataModels>
/*==
    @FormUrlEncoded
    @POST("api/register")
    fun register (@Field("email")eMAIL:String,
                  @Field("password")passWORD:String):Call<RegisterDataModels>
*/

}