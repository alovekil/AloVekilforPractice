package com.karimmammadov.alovekilforpractice.models

data class LoginResponse (
        val token:String,
        val user_type:String,
        val non_field_errors:ArrayList<String>,

        )