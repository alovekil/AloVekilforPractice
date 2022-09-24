package com.karimmammadov.alovekilforpractice.api.forlawyer

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientForLawyer {

    private val AUTH = "Basic " + Base64.encodeToString("KerimM:kerim12345".toByteArray(), Base64.NO_WRAP)

    private  val BASE_URL = "http://38.242.221.247/api/users/"

    private val  okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(),original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ApiForLawyer by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiForLawyer::class.java)
    }

}