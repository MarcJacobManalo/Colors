package com.david.colors.api_retrofit


import io.reactivex.Observable
import retrofit2.http.GET

interface GetUserService {

    @GET("api/unknown")
    fun getAllColors(): Observable<ColorListApi>

}

