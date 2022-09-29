package com.karimmammadov.alovekilforpractice.models.forforgetpassword

data class ForgetpasswordResponse (
        val user_token:String,
        val message:String,
        val status:String,
        val code:Int
        )