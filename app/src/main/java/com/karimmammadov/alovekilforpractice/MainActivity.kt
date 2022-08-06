package com.karimmammadov.alovekilforpractice

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karimmammadov.alovekilforpractice.fragments.PhoneNumberFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun signUp(view:View){
        val intent = Intent(this@MainActivity,ChooseSignUpActivity::class.java)
        startActivity(intent)
        //big
    }
}