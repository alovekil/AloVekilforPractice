package com.karimmammadov.alovekilforpractice

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.karimmammadov.alovekilforpractice.PinCode.CreatePasswordActivity
import com.karimmammadov.alovekilforpractice.api.RetrofitClient
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import com.karimmammadov.alovekilforpractice.models.CustomerModels
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_customer_regstr.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegstrActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var customerModels: CustomerModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_regstr)

        sharedPreferences = this!!.getSharedPreferences("customer", Context.MODE_PRIVATE)

        bck_signActivity.setOnClickListener {
            val intent = Intent(this@CustomerRegstrActivity,ChooseSignUpActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }

        val loginSharedPreferences = getSharedPreferences("Myprefs",0)
        val editor = loginSharedPreferences.edit()



        savebtn.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val name = editFirstName.text.toString().trim()
            val secondName = editSecondName.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirmPassword = editConfirmPassword.text.toString().trim()
            val phonenumberCustomer = editphoneNumberCustomer.text.toString().trim()
            if (email.isEmpty()){
                editEmail.error = "Email required"
                editEmail.requestFocus()
            }
            if (name.isEmpty()){
                editFirstName.error = "First name required"
                editFirstName.requestFocus()
            }
            if (secondName.isEmpty()){
                editSecondName.error = "Second name required"
                editSecondName.requestFocus()
            }
            if (password.isEmpty()){
                editPassword.error = "Password required"
                editPassword.requestFocus()

            }
            if (confirmPassword.isEmpty()){
                editConfirmPassword.error = "Confirm Password required"
                editConfirmPassword.requestFocus()
            }
            if (phonenumberCustomer.isEmpty()){
                editphoneNumberCustomer.error = "Phone number required"
                editphoneNumberCustomer.requestFocus()
            }

            customerModels = CustomerModels(email,name,secondName,password,confirmPassword,phonenumberCustomer)

            RetrofitClient.instance.createUser(customerModels)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext,response.body()?.response, Toast.LENGTH_SHORT).show()
                        editor.putString(MyConstants.userName,name)
                        editor.putString(MyConstants.userSecondName,secondName)
                        editor.putString(MyConstants.userEmail,email)
                        editor.putBoolean(MyConstants.args,true)
                        editor.commit()
                       val intent = Intent(this@CustomerRegstrActivity,CreatePasswordActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}