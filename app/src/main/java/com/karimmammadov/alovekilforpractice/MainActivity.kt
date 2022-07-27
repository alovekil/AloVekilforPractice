package com.karimmammadov.alovekilforpractice

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

    fun customerOnClick(view: View){
        btn_customer.setBackgroundResource(R.drawable.bgn_pinkbutton)
        btn_customer.setTextColor(Color.parseColor("#FFFFFF"))
        btn_lawyer.setBackgroundResource(R.drawable.bg_button)
        btn_lawyer.setTextColor(Color.parseColor("#000000"))

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentNumber = PhoneNumberFragment()
        fragmentTransaction.replace(R.id.frameLayout,fragmentNumber).commit()
    }

    fun vekilOnClick(view: View){
        btn_lawyer.setBackgroundResource(R.drawable.bgn_pinkbutton)
        btn_lawyer.setTextColor(Color.parseColor("#FFFFFF"))
        btn_customer.setBackgroundResource(R.drawable.bg_button)
        btn_customer.setTextColor(Color.parseColor("#000000"))
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentNumber = PhoneNumberFragment()
        fragmentTransaction.replace(R.id.frameLayout,fragmentNumber).commit()
    }
}