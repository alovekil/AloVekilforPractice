package com.karimmammadov.alovekilforpractice.api.forlogin

import com.karimmammadov.alovekilforpractice.models.forlogin.LoginResponse
import com.karimmammadov.alovekilforpractice.models.forlogin.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiForLogin {
    @POST("login/")
     fun userlogin(
    @Body users: Users
    ): Call<LoginResponse>
}