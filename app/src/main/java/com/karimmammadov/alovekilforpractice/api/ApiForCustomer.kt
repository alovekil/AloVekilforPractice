package com.karimmammadov.alovekilforpractice.api

import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import com.karimmammadov.alovekilforpractice.models.LawyerLanguageItems
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiForCustomer {

        @FormUrlEncoded
        @POST("customer/register/")
    fun createUser(
        @Field("email") email:String,
        @Field("first_name") name:String,
        @Field("last_name") last_name:String,
        @Field("phone") phone:String,
        @Field("password") password:String,
        @Field("password2") password2:String
    ) : Call<DefaultResponse>

}