package com.karimmammadov.alovekilforpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ChooseSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_sign_up)
    }

    fun lawyerSignUp(view: View){
        val intent = Intent(this@ChooseSignUpActivity,LawyerOtpActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun customerSignUp(view: View){
        val intent = Intent(this@ChooseSignUpActivity,CustomerOtpActivity::class.java)
        startActivity(intent)
        finish()
    }
}