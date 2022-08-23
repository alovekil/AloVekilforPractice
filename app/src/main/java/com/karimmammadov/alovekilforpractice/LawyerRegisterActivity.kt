package com.karimmammadov.alovekilforpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.karimmammadov.alovekilforpractice.adapters.ViewPagerAdapter
import com.karimmammadov.alovekilforpractice.fragments.LawyerRegisterPage1
import com.karimmammadov.alovekilforpractice.fragments.LawyerRegisterPage2

class LawyerRegisterActivity : AppCompatActivity() {
    lateinit var viewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_register)

        viewPager  = findViewById(R.id.pager)

        val fragments : ArrayList<Fragment> = arrayListOf(
            LawyerRegisterPage1(),
            LawyerRegisterPage2()
        )
        val adapter = ViewPagerAdapter(fragments,this)
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        }else{
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}