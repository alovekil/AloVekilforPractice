package com.karimmammadov.alovekilforpractice

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_choose_sign_up.*

class ChooseSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_sign_up)

        Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT)

        btn_backActivity.setOnClickListener {
            val intent = Intent(this@ChooseSignUpActivity,MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
    }

    fun lawyerSignUp(view: View){
        val intent = Intent(this@ChooseSignUpActivity,LawyerOtpActivity::class.java)
        startActivity(intent)
    }

    fun customerSignUp(view: View){
        val intent = Intent(this@ChooseSignUpActivity,CustomerOtpActivity::class.java)
        startActivity(intent)
    }
}