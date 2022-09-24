package com.karimmammadov.alovekilforpractice.api.forlogin

import com.karimmammadov.alovekilforpractice.models.LoginResponse
import com.karimmammadov.alovekilforpractice.models.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiForLogin {
    @POST("login/")
     fun userlogin(
    @Body users: Users
    ): Call<LoginResponse>
}