package com.karimmammadov.alovekilforpractice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.karimmammadov.alovekilforpractice.PinCode.Create_Password_Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun signUp(view:View){
        val intent = Intent(this@MainActivity,Create_Password_Fragment::class.java)
        startActivity(intent)
        finish()
        //big
    }
}