package com.karimmammadov.alovekilforpractice

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.constant.MyConstants

class SplashScreenFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_splash_screen, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (sharedPreferences.getBoolean("create_password", false)) {
                   findNavController().navigate(R.id.action_splashScreenFragment_to_pinCodeFragment)
                } else {
                    if(sharedPreferences.getInt("savefragments",0)==0)
                    {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_signInUpFragment) }
                    else{
                        findNavController().navigate(sharedPreferences.getInt("savefragments",0))
                    }
                    }
                }
            }.start()
        return view
    }
}