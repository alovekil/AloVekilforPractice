package com.karimmammadov.alovekilforpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.karimmammadov.alovekilforpractice.api.RetrofitClient
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_customer_regstr.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegstrActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_regstr)

        savebtn.setOnClickListener {
            val email = mail.text.toString().trim()
            val name = firstname.text.toString().trim()
            val secondName = surname.text.toString().trim()
            val password = Password.text.toString().trim()
            val confirmPassword = confirmPassword.text.toString().trim()
            val phoneNumber = phone.text.toString().trim()


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