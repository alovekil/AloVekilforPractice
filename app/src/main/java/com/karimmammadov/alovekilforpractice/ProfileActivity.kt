package com.karimmammadov.alovekilforpractice

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileSharedPreferences = getSharedPreferences("Myprefs",0)
        val profileName = profileSharedPreferences.getString(MyConstants.userName,null)
        val profileSecondName = profileSharedPreferences.getString(MyConstants.userSecondName,null)
        val profileEmail = profileSharedPreferences.getString(MyConstants.userEmail,null)

        profileUserName.text = profileName + " " + profileSecondName
        profileEmailxml.text = profileEmail
    }

    companion object{
        fun intent(context: Context): Intent {
            val intent = Intent(context,ProfileActivity::class.java)
            return intent
        }
    }
}