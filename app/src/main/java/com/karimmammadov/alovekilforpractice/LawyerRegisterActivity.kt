package com.karimmammadov.alovekilforpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_lawyer_register.*
import me.relex.circleindicator.CircleIndicator3

class LawyerRegisterActivity : AppCompatActivity() {
    private var layoutsList = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_register)

        view_pager2.adapter = ViewPageAdapter(layoutsList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

    private fun addToList(layout: View){
        for(i in 1..2){
            layoutsList.add(layout)
        }
    }
}