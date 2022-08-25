package com.karimmammadov.alovekilforpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karimmammadov.alovekilforpractice.PinCode.Create_Password
import com.karimmammadov.alovekilforpractice.PinCode.PinCodeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun signUp(view:View){
        val intent = Intent(this@MainActivity,Create_Password::class.java)
        startActivity(intent)
        finish()
        //big
    }
}