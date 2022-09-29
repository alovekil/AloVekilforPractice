package com.karimmammadov.alovekilforpractice.api.forcustomer

import com.karimmammadov.alovekilforpractice.models.forcustomer.CustomerModels
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiForCustomer {

        @POST("customer/register/")
    fun createUser(
       @Body customerModels: CustomerModels
    ) : Call<DefaultResponse>

}