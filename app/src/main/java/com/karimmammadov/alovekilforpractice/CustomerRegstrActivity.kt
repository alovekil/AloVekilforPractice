package com.karimmammadov.alovekilforpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.karimmammadov.alovekilforpractice.api.RetrofitClient
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_customer_register.*
import kotlinx.android.synthetic.main.activity_customer_register.firstname
import kotlinx.android.synthetic.main.activity_customer_regstr.*
import kotlinx.android.synthetic.main.activity_enter_number.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_regstr)

        val intent = intent
        val phoneNumber = intent.getStringExtra("phone_number").toString().trim()
        tv_number.text =phoneNumber
        savebtn.setOnClickListener {
            val email = mail.text.toString().trim()
            val name = firstname.text.toString().trim()
            val secondName = surname.text.toString().trim()

            if (email.isEmpty()){
                mail.error = "Email required"
                mail.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                firstname.error = "First name required"
                firstname.requestFocus()
                return@setOnClickListener
            }
            if (secondName.isEmpty()){
                surname.error = "Second name required"
                surname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(email,surname,name)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext,response.body()?.response, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        if (mail != email){
                            Toast.makeText(applicationContext,"Passwords do not match",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
}