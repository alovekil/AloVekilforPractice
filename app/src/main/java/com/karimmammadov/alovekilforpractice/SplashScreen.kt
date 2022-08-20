package com.karimmammadov.alovekilforpractice

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import com.karimmammadov.alovekilforpractice.constant.MyConstants

class SplashScreen : AppCompatActivity() {
    lateinit var  handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       val countDownTimer  = object : CountDownTimer(3000,1000) {
           override fun onTick(p0: Long) {

           }

           override fun onFinish() {
               val mySharedPreferences = getSharedPreferences("Myprefs",0)
               val logedin = mySharedPreferences.getBoolean(MyConstants.args,false)
               if(logedin){
                   val intent =Intent (this@SplashScreen,PinCodeActivity::class.java)
                   startActivity(intent)
                   finish()
               }else{
                   val intent=Intent(this@SplashScreen,MainActivity::class.java)
                   startActivity(intent)
                   finish()
               }
           }

       }.start()

    }
}