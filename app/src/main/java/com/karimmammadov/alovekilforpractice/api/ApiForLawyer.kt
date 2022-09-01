package com.karimmammadov.alovekilforpractice.api

import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import com.karimmammadov.alovekilforpractice.models.LawyerModels
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiForLawyer {


    @POST("lawyer/register/")

    fun createUserLawyer(
        @Body
        lawyerModels: LawyerModels
    ) : Call<DefaultResponse>
}