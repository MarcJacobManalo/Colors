package com.david.colors.api_retrofit


import retrofit2.Call
import retrofit2.http.GET

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors(): Call<ColorListApi>

}

