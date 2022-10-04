package com.karimmammadov.alovekilforpractice.api.forlogin

import com.karimmammadov.alovekilforpractice.models.forlogin.LoginResponse
import com.karimmammadov.alovekilforpractice.models.forlogin.Users
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiForLogin {
    @Multipart
    @POST("login/")
     fun userlogin(
        @Part ("username") name:RequestBody,
        @Part ("password") password:RequestBody,
    ): Call<LoginResponse>
}