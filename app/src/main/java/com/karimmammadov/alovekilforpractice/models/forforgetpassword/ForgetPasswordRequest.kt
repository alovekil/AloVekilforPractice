package com.karimmammadov.alovekilforpractice.models.forforgetpassword

data class ForgetPasswordRequest (
    val phone:String,
    val password:String,
    val password2:String
        )