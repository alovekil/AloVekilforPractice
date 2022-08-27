package com.karimmammadov.alovekilforpractice.PinCode

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.databinding.FragmentCreatePasswordBinding


class Create_Password_Fragment : Fragment() {

    var binding:FragmentCreatePasswordBinding?=null
    var radioList1: ArrayList<RadioButton> = ArrayList()
    var radioList2: ArrayList<RadioButton> = ArrayList()
    var password1:String? = ""
    var gpassword2:String? = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
 override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // binding = ActivityCreatePasswordBinding.inflate(inflater,R.layout.fragment_create__password_,false)
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_create__password_,container,false)
        val view=binding!!.root
        sharedPreferences = this.requireContext().getSharedPreferences("password", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()
        radioList2.add(binding!!.circle1);
        radioList2.add(binding!!.circle2);
        radioList2.add(binding!!.circle3);
        radioList2.add(binding!!.circle4);
        radioList1.add(binding!!.circle5);
        radioList1.add(binding!!.circle6);
        radioList1.add(binding!!.circle7);
        radioList1.add(binding!!.circle8);
        binding!!.number0.setOnClickListener { view -> passwordCheck("0") }
        binding!!.number1.setOnClickListener { view -> passwordCheck("1") }
        binding!!.number2.setOnClickListener { view -> passwordCheck("2") }
        binding!!.number3.setOnClickListener { view -> passwordCheck("3") }
        binding!!.number4.setOnClickListener { view -> passwordCheck("4") }
        binding!!.number5.setOnClickListener { view -> passwordCheck("5") }
        binding!!.number6.setOnClickListener { view -> passwordCheck("6") }
        binding!!.number7.setOnClickListener { view -> passwordCheck("7") }
        binding!!.number8.setOnClickListener { view -> passwordCheck("8") }
        binding!!.number9.setOnClickListener { view -> passwordCheck("9") }

    binding!!.deletenumbers.setOnClickListener{
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
        checkpaswordequal()
    }

    private fun checkpaswordequal() {
        if (password1!!.length == 4 && gpassword2!!.length == 4) {
            if (password1.equals(gpassword2)) {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_create_Password_Fragment2_to_profileActivity) }
                editor.putString("password", password1)
                editor.putBoolean("create_password", true)
                editor.commit()

                Toast.makeText(this.activity, "Succesfully", Toast.LENGTH_LONG)
            }
        }   else{
            password1=""
            radio1True(password1!!.length)
            gpassword2=""
            radio2True(gpassword2!!.length)
        }

    }
}