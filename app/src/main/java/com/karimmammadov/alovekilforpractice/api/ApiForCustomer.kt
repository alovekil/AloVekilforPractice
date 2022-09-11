package com.karimmammadov.alovekilforpractice.api

import com.karimmammadov.alovekilforpractice.models.CustomerModels
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import com.karimmammadov.alovekilforpractice.models.LawyerLanguageItems
import retrofit2.Call
import retrofit2.http.*

interface ApiForCustomer {

        @POST("customer/register/")
    fun createUser(
       @Body customerModels: CustomerModels
    ) : Call<DefaultResponse>

}