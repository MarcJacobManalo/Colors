package com.david.colors.model.api

import com.david.colors.model.`interface`.GetUserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://reqres.in/"

class RetrofitInit
{
   companion object{
        fun create(): GetUserService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(GetUserService::class.java)
        }
   }
}
