package com.karimmammadov.alovekilforpractice.api

import com.karimmammadov.alovekilforpractice.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiForLawyer {
    @FormUrlEncoded
    @POST("lawyer/register/")
    fun createUserLawyer(
        @Field("email") email:String,
        @Field("first_name") name:String,
        @Field("last_name") last_name:String,
        @Field("phone") phone:String,
        @Field("password") password:String,
        @Field("password2") password2:String,
        @Field("lawyer") lawyer: Lawyer
    ) : Call<DefaultResponse>

    @GET("service-languages")
    fun getLanguageData() : Call<List<LawyerLanguageItems>>

    @GET("service-types")
    fun getAreasData() : Call<List<LawyerAreaTypes>>
}