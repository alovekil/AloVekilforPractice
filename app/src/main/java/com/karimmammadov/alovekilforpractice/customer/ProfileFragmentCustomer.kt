package com.karimmammadov.alovekilforpractice.customer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.constant.MyConstants

import kotlinx.android.synthetic.main.fragment_profile_customer.*
import kotlinx.android.synthetic.main.fragment_profile_customer.view.*

class ProfileFragmentCustomer : Fragment() {
    private lateinit var profileSharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile_customer, container, false)

        profileSharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        val profileName = profileSharedPreferences.getString("customerName1","null")!!
        val profileSecondName = profileSharedPreferences.getString("customerSurname1","null")!!
        val profileEmail = profileSharedPreferences.getString("customerEmail1","null")!!
        view.profileUserName.text = profileName + " " + profileSecondName
        view.profileEmailxml.text = profileEmail

        return view
    }
}