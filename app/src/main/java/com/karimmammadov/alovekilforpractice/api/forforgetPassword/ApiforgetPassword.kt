package com.karimmammadov.alovekilforpractice.api.forforgetPassword

import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetPasswordRequest
import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetpasswordResponse
import com.karimmammadov.alovekilforpractice.models.forlogin.LoginResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiforgetPassword {
 @Multipart
@PUT("reset-password/")
 fun resetpassword(
     @Part ("phone") name: RequestBody,
     @Part ("password") password: RequestBody,
     @Part ("password2") password2: RequestBody,
     @Header("Authorization")Authorization:String
):Call<ForgetpasswordResponse>
}