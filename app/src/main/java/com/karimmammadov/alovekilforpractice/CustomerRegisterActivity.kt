package com.karimmammadov.alovekilforpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.karimmammadov.alovekilforpractice.api.RetrofitClient
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_customer_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)

        val intent = intent
        val phoneNumber = intent.getStringExtra("phone_number").toString().trim()
        tv_phone.text =phoneNumber
        btn_signUp.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val name = editFirstName.text.toString().trim()
            val secondName = editSecondName.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirmPassword = editConfirmPassword.text.toString().trim()

            if (email.isEmpty()){
                editEmail.error = "Email required"
                editEmail.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                editFirstName.error = "First name required"
                editFirstName.requestFocus()
                return@setOnClickListener
            }
            if (secondName.isEmpty()){
                editSecondName.error = "Second name required"
                editSecondName.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                editPassword.error = "Password required"
                editPassword.requestFocus()
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()){
                editConfirmPassword.error = "Confirm Password required"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(email,name,secondName,phoneNumber,password, confirmPassword)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext,response.body()?.response, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        if (password != confirmPassword){
                            Toast.makeText(applicationContext,"Passwords do not match",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
}