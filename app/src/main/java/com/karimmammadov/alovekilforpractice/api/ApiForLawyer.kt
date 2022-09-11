package com.karimmammadov.alovekilforpractice.api

import com.karimmammadov.alovekilforpractice.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiForLawyer {

    @POST("lawyer/register/")
    fun createUserLawyer(
      @Body lawyerModels: LawyerModels
    ) : Call<DefaultResponse>

    @GET("service-languages")
    fun getLanguageData() : Call<List<LawyerLanguageItems>>

    @GET("service-types")
    fun getAreasData() : Call<List<LawyerAreaTypes>>
}