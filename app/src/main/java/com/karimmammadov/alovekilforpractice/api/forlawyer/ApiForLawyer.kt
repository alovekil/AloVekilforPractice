package com.karimmammadov.alovekilforpractice.api.forlawyer

import com.karimmammadov.alovekilforpractice.models.*
import com.karimmammadov.alovekilforpractice.models.forlawyer.LawyerAreaTypes
import com.karimmammadov.alovekilforpractice.models.forlawyer.LawyerLanguageItems
import com.karimmammadov.alovekilforpractice.models.forlawyer.LawyerModels
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiForLawyer {

    @Multipart
    @POST("lawyer/register/")
    fun createUserLawyer(
      @Body lawyerModels: LawyerModels/*,
       @Part Image:MultipartBody.Part*/
    ) : Call<DefaultResponse>

    @GET("service-languages")
    fun getLanguageData() : Call<List<LawyerLanguageItems>>

    @GET("service-types")
    fun getAreasData() : Call<List<LawyerAreaTypes>>



}