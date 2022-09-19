package com.karimmammadov.alovekilforpractice.PinCode

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.*


class CreatePasswordFragment : Fragment() {

    var radioList1: ArrayList<RadioButton> = ArrayList()
    var radioList2: ArrayList<RadioButton> = ArrayList()
    var password1:String? = ""
    var gpassword2:String? = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_password_customer, container, false)

        view.EnterConfirmPassword.visibility = View.GONE
        sharedPreferences = requireContext().getSharedPreferences("lawyer", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()
        radioList1.add(view.circle1)
        radioList1.add(view.circle2)
        radioList1.add(view.circle3)
        radioList1.add(view.circle4)
        radioList2.add(view.circle5)
        radioList2.add(view.circle6)
        radioList2.add(view.circle7)
        radioList2.add(view.circle8)
        view.number0.setOnClickListener { view -> passwordCheck("0") }
        view.number1.setOnClickListener { view -> passwordCheck("1") }
        view.number2.setOnClickListener { view -> passwordCheck("2") }
        view.number3.setOnClickListener { view -> passwordCheck("3") }
        view.number4.setOnClickListener { view -> passwordCheck("4") }
        view.number5.setOnClickListener { view -> passwordCheck("5") }
        view.number6.setOnClickListener { view -> passwordCheck("6") }
        view.number7.setOnClickListener { view -> passwordCheck("7") }
        view.number8.setOnClickListener { view -> passwordCheck("8") }
        view.number9.setOnClickListener { view -> passwordCheck("9") }

        view.deletenumbers.setOnClickListener{
            if (password1!!.length > 0) {
                password1 = password1!!.substring(0, password1!!.length - 1)
                radio1True(password1!!.length)
                System.out.println(password1)
            }
            else if (gpassword2!!.length > 0) {
                gpassword2 = gpassword2!!.substring(0, gpassword2!!.length - 1)
                radio2True(gpassword2!!.length)
                System.out.println(gpassword2)
            }
        }
        return view
    }

private fun radio2True(length: Int) {
    for(i in 0..3 ){
        if(i<length){
            radioList2[i].isChecked = true
        }
        else{
            radioList2[i].isChecked = false
        }
    }
}

private fun radio1True(length: Int) {
    for(i in 0..3 ){
        if(i<length){
            radioList1[i].isChecked = true
        }
        else{
            radioList1[i].isChecked = false
        }
    }
}

private fun passwordCheck(s: String) {


    if(password1!!.length<4){
        password1+=s
        radio1True(password1!!.length)
    }
    else if(gpassword2!!.length<4){
        gpassword2+=s
        radio2True(gpassword2!!.length)
    }

    if(password1!!.length == 4){
        view?.EnterConfirmPassword?.visibility = View.VISIBLE
    }
    checkpaswordequal()
}

private fun checkpaswordequal() {
    if (password1!!.length == 4 && gpassword2!!.length == 4) {
        if (password1.equals(gpassword2)) {
            editor.putString("password", password1)
            editor.putBoolean("create_password", true)
            editor.commit()
            val islawyer = sharedPreferences.getBoolean("isLawyer",false)
            if(islawyer){
                findNavController().navigate(R.id.action_createPasswordCustomer_to_alertDialogLawyer)
            }else{
                findNavController().navigate(R.id.action_createPasswordCustomer_to_alertDialogLawyer)
            }
            Toast.makeText(requireContext(), "Succesfully", Toast.LENGTH_LONG)
        }
        else{
            password1=""
            radio1True(password1!!.length)
            gpassword2=""
            radio2True(gpassword2!!.length)
        }
    }

}
}