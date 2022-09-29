package com.karimmammadov.alovekilforpractice.api.forforgetPassword

import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetPasswordRequest
import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetpasswordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiforgetPassword {
@PUT("reset-password/")
    fun createnewPassword(
    @Body forgetpasswordRequest: ForgetPasswordRequest
/*    @Path("token") token:String,
    @Field("phone") phone:String,
    @Field("password") password:String,
    @Field("password2") password2:String*/
/*
    forgetpasswordRequest:ForgetPasswordRequest
*/
):Call<ForgetpasswordResponse>
}