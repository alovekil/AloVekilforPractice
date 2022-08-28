package com.karimmammadov.alovekilforpractice

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.karimmammadov.alovekilforpractice.PinCode.PinCodeActivity
import com.karimmammadov.alovekilforpractice.constant.MyConstants

class SplashScreen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var  handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = this@SplashScreen.getSharedPreferences("password", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        val countDownTimer  = object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (sharedPreferences.getBoolean("create_pasword", false)) {
                    val mySharedPreferences = getSharedPreferences("Myprefs", 0)
                    val logedin = mySharedPreferences.getBoolean(MyConstants.args, false)
                    if (logedin) {
                        val intent = Intent(this@SplashScreen, PinCodeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }.start()


       }

    }
