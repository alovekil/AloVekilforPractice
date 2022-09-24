package com.karimmammadov.alovekilforpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_in_up.*
import kotlinx.android.synthetic.main.fragment_sign_in_up.view.*


class SignInUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sign_in_up, container, false)


        view.button_signUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInUpFragment_to_chooseSignUpFragment)
        }
        view.login.setOnClickListener {
            findNavController().navigate(R.id.action_signInUpFragment_to_signInFragment)
        }
        return  view
    }

}