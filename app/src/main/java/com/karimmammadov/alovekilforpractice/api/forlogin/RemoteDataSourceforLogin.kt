package com.karimmammadov.alovekilforpractice.api.forlogin

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceforLogin{

    companion object {
        private val AUTH = "Basic " + Base64.encodeToString("Kerim:kerim123".toByteArray(), Base64.NO_WRAP)
        private val BASE_URLLOGIN = "http://38.242.221.247/api/users/"
    }


    fun <Api> buildapi(
        api: Class<Api>,
        authtoken:String?=null
    ):Api{
        return Retrofit.Builder()
            .baseUrl(BASE_URLLOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(api)
    }
    private val  okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(),original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()


/*   val instance2:ApiForLogin by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URLLOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitClient.okHttpClient)
            .build()
        retrofit.create(ApiForLogin::class.java)
    }*/
}