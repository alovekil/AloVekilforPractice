package com.karimmammadov.alovekilforpractice.api.forlogin

import okhttp3.ResponseBody

//This class handled the all error messages
sealed class Resource <out T>{//sealed class we can implement different type of data classes
    data class Success<out T>(val value:T): Resource<T>()
    data class Failure<out T>(
        val isNetworkError1:Boolean?,
        val errorcodes1: Int?,
        val errorBody:ResponseBody?
    ): Resource<Nothing>()
}