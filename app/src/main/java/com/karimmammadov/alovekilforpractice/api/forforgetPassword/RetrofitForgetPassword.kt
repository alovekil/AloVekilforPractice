package com.karimmammadov.alovekilforpractice.api.forforgetPassword

import android.util.Base64
import com.karimmammadov.alovekilforpractice.api.forcustomer.ApiForCustomer
import com.karimmammadov.alovekilforpractice.api.forcustomer.RetrofitClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitForgetPassword {
    val AUTH = "Basic " + Base64.encodeToString("Kerim:kerim123".toByteArray(), Base64.NO_WRAP)
    private  val BASE_URL = "http://38.242.221.247/api/"
    private val  okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(),original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ApiforgetPassword by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiforgetPassword::class.java)
    }
}