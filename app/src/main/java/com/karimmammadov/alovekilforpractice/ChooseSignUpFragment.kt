package com.karimmammadov.alovekilforpractice

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import kotlinx.android.synthetic.main.fragment_choose_sign_up.*
import kotlinx.android.synthetic.main.fragment_choose_sign_up.view.*

class ChooseSignUpFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_choose_sign_up, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs",Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        view.btn_backsigninupfragment.setOnClickListener {
          findNavController().navigate(R.id.action_chooseSignUpFragment_to_signInUpFragment)
        }

        view.appCompatButtonCustomer.setOnClickListener {
            editor.putString("usertype","customer").apply()
            editor.commit()
            findNavController().navigate(R.id.action_chooseSignUpFragment_to_customerOtpFragment)
        }
        view.appCompatButtonLawyer.setOnClickListener {
            editor.putString("usertype","lawyer").apply()
            editor.commit()
            findNavController().navigate(R.id.action_chooseSignUpFragment_to_lawyerOtpFragment)
        }
        return view
    }
}