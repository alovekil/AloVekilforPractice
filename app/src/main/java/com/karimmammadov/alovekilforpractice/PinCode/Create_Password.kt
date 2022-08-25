package com.karimmammadov.alovekilforpractice.PinCode

import android.app.ActivityOptions
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import java.util.ArrayList
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.karimmammadov.alovekilforpractice.ChooseSignUpActivity
import com.karimmammadov.alovekilforpractice.ProfileActivity
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.activity_create_password.*
import kotlinx.android.synthetic.main.activity_pin_code.*
import kotlinx.android.synthetic.main.activity_pin_code.circle1
import kotlinx.android.synthetic.main.activity_pin_code.circle2
import kotlinx.android.synthetic.main.activity_pin_code.circle3
import kotlinx.android.synthetic.main.activity_pin_code.circle4
import kotlinx.android.synthetic.main.activity_pin_code.number1
import kotlinx.android.synthetic.main.activity_pin_code.number2
import kotlinx.android.synthetic.main.activity_pin_code.number3
import kotlinx.android.synthetic.main.activity_pin_code.number4
import kotlinx.android.synthetic.main.activity_pin_code.number5
import kotlinx.android.synthetic.main.activity_pin_code.number6
import kotlinx.android.synthetic.main.activity_pin_code.number7
import kotlinx.android.synthetic.main.activity_pin_code.number8
import kotlinx.android.synthetic.main.activity_pin_code.number9
import kotlinx.android.synthetic.main.activity_pin_code.deletenumbers as deletenumbers1


class Create_Password : AppCompatActivity() {

    var sharedPreferenceManager: SharedPreferenceManager? = null
    val radioList1: ArrayList<RadioButton> = ArrayList()
    var radioList2: ArrayList<RadioButton> = ArrayList()

    var password1:String? = ""
    var gpassword2:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
        sharedPreferenceManager = SharedPreferenceManager(getActivity(this,password1.hashCode(),intent,Context.MODE_PRIVATE))
        radioList1.add(circle1)
        radioList1.add(circle2)
        radioList1.add(circle3)
        radioList1.add(circle4)
        radioList2.add(circle1)
        radioList2.add(circle2)
        radioList2.add(circle3)
        radioList2.add(circle4)
        pin_code_first.visibility=VISIBLE
        pin_code_second.visibility= GONE
        number1.setOnClickListener{
            passwordcheck("1")
        }

        number2.setOnClickListener{
            passwordcheck("2")
        }
        number3.setOnClickListener{
            passwordcheck("3")
        }
        number4.setOnClickListener{
            passwordcheck("4")
        }
        number5.setOnClickListener{
            passwordcheck("5")
        }
        number6.setOnClickListener{
            passwordcheck("6")
        }
        number7.setOnClickListener{
            passwordcheck("7")
        }
        number8.setOnClickListener{
            passwordcheck("8")
        }
        number9.setOnClickListener{
            passwordcheck("9")
        }

        deletenumbers.setOnClickListener {
            if(gpassword2!!.length>0){
                gpassword2=gpassword2?.substring(0,password1!!.length-1)
                radioList2.get(gpassword2!!.length)
                println(gpassword2)
            }
            else if(password1!!.length>0){
                password1=password1?.substring(0,password1!!.length-1)
                radioList1.get(password1!!.length)
                println(password1)

            }
        }
    }



    private fun passwordcheck(number:String) {
       if(password1!!.length<=3){
           password1+=number
           radioList1.get(password1!!.length)
           pin_code_first.visibility= GONE
           pin_code_second.visibility= VISIBLE
       }
        else if(gpassword2!!.length<=3){
            gpassword2+=number
           radioList2.get(gpassword2!!.length)
        }
        checkpaswordequal()
    }

    private fun checkpaswordequal() {
        if (password1!!.length==4 && gpassword2!!.length==4){
            if(password1.equals(gpassword2)){
                sharedPreferenceManager?.setValue("password", password1!!)
                sharedPreferenceManager?.setValue("create_password", true)

                Toast.makeText(this,"Succesfully",Toast.LENGTH_LONG)
                /*val intent = Intent(this@Create_Password, ProfileActivity::class.java)
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                finish()*/
            }
            else{
                password1=""
                radio1true(password1!!.length)
                gpassword2=""
                radio2true(gpassword2!!.length)
            }
        }

    }

    private fun radio2true(size:Int) {
        for(i in 0..4 ){
            if(i<size){
                radioList2.get(i).isChecked
            }
            else{
                radioList2.get(i).isChecked
            }
        }
    }

    private fun radio1true(size:Int) {
        for(i in 0..4 ){
            if(i<size){
                radioList1.get(i).isChecked
            }
            else{
                radioList1.get(i).isChecked
            }
        }
    }
}