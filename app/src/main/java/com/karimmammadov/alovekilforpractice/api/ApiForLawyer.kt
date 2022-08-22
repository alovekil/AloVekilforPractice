package com.karimmammadov.alovekilforpractice.api

import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiForLawyer{
/*
    @FormUrlEncoded
    @POST("register")
    fun createLawyer(
        @Field("first_name") name:String,
        @Field("last_name") last_name:String,
        @Field("father_name") father_name:String,
        @Field("gender") gender:String,
        @Field("date_of_birth") date_of_birth:String,
        @Field("university") university:String,
        @Field("email") email:String,
        @Field("service_language") service_language:String,
        @Field("service_areas") service_areas:Int,

    @Part image:MultipartBody.Part,
    @Part ("certificate")certificate:RequestBody
    )




    operator fun invoke():ApiForLawyer{
     return Retrofit.Builder()
         .baseUrl("")//http/api code write here http://38.242.221.247/imageuploader/lawyer/
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(ApiForLawyer::class.java)
    }

 */
}