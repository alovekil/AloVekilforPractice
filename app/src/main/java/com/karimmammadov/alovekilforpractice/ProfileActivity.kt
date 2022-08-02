package com.karimmammadov.alovekilforpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent = intent
        val firstN = intent.getStringExtra("name")
        val secondN = intent.getStringExtra("secondname")
        val useremail = intent.getStringExtra("email")
        tv_userFullName.text = firstN + " " + secondN
        tv_userEmail.text = useremail
    }
}