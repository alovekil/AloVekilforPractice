package com.karimmammadov.alovekilforpractice.PinCode

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karimmammadov.alovekilforpractice.ProfileActivity
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.activity_create_password.*

class CreatePasswordActivity : AppCompatActivity() {

  //  var binding: ActivityCreatePasswordBinding?=null
    var radioList1: ArrayList<RadioButton> = ArrayList()
    var radioList2: ArrayList<RadioButton> = ArrayList()
    var password1:String? = ""
    var gpassword2:String? = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
EnterConfirmPassword.visibility = View.GONE

     //   binding= DataBindingUtil.inflate(layoutInflater,R.layout.activity_create_password,null,false)
        sharedPreferences = this!!.getSharedPreferences("password", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()
        radioList1.add(circle1)
        radioList1.add(circle2)
        radioList1.add(circle3)
        radioList1.add(circle4)
        radioList2.add(circle5)
        radioList2.add(circle6)
        radioList2.add(circle7)
        radioList2.add(circle8)
       number0.setOnClickListener { view -> passwordCheck("0") }
        number1.setOnClickListener { view -> passwordCheck("1") }
       number2.setOnClickListener { view -> passwordCheck("2") }
      number3.setOnClickListener { view -> passwordCheck("3") }
       number4.setOnClickListener { view -> passwordCheck("4") }
       number5.setOnClickListener { view -> passwordCheck("5") }
      number6.setOnClickListener { view -> passwordCheck("6") }
        number7.setOnClickListener { view -> passwordCheck("7") }
        number8.setOnClickListener { view -> passwordCheck("8") }
       number9.setOnClickListener { view -> passwordCheck("9") }

        deletenumbers.setOnClickListener{
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
            EnterConfirmPassword.visibility = View.VISIBLE
        }
        checkpaswordequal()
    }

    private fun checkpaswordequal() {
        if (password1!!.length == 4 && gpassword2!!.length == 4) {
            if (password1.equals(gpassword2)) {
             //   view?.let { Navigation.findNavController(it).navigate(R.id.action_create_Password_Fragment2_to_profileActivity) }
                editor.putString("password", password1)
                editor.putBoolean("create_password", true)
                editor.commit()
                val intent = Intent(this@CreatePasswordActivity,ProfileActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this@CreatePasswordActivity, "Succesfully", Toast.LENGTH_LONG)
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