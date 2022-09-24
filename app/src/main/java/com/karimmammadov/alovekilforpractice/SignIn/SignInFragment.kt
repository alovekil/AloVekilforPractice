package com.karimmammadov.alovekilforpractice.SignIn

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.api.forcustomer.RetrofitClient
import com.karimmammadov.alovekilforpractice.api.forlawyer.ApiForLawyer

import com.karimmammadov.alovekilforpractice.api.forlogin.ApiForLogin
import com.karimmammadov.alovekilforpractice.api.forlogin.RemoteDataSourceforLogin
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import com.karimmammadov.alovekilforpractice.databinding.FragmentSignInBinding
import com.karimmammadov.alovekilforpractice.models.LoginResponse
import com.karimmammadov.alovekilforpractice.models.LawyerAreaTypes
import com.karimmammadov.alovekilforpractice.models.Users
import kotlinx.android.synthetic.main.fragment_customer_otp.view.*
import kotlinx.android.synthetic.main.fragment_customer_otp.view.btn_logIn
import kotlinx.android.synthetic.main.fragment_customer_otp.view.phoneNumberSignIn
import kotlinx.android.synthetic.main.fragment_lawyer_register2.view.*
import kotlinx.android.synthetic.main.fragment_lawyer_register2.view.editPasswordLogin
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignInFragment :Fragment() {
    val remoteDataSourceforLogin=RemoteDataSourceforLogin()
    lateinit var users: Users
    private val BASE_URLLOGIN = "http://38.242.221.247/api/users/"
      private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_sign_in, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()
        view.forgetthepassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgetThePasswordFragment)
        }
        view.btn_logIn.setOnClickListener {
            val number="+994"+view.phoneNumberSignIn.text.toString().trim()
            val password=view.editPasswordLogin.text.toString().trim()

            if(number.isEmpty()){
                view.phoneNumberSignIn.error="Number required"
                view.phoneNumberSignIn.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                view.editPasswordLogin.error="Password required"
                view.editPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            users=Users(number,password)
            getMyAreasData()
        }
        return view
    }
    private  fun getMyAreasData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URLLOGIN)
            .build()
            .create(ApiForLogin::class.java)
        val retrofitData = retrofitBuilder.userlogin(users)

            retrofitBuilder.userlogin(users)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if(response.body()?.non_field_errors == null){
                        editor.putString("tokenvalue", response.body()!!.token)
                        editor.putString("usertype",response.body()!!.user_type)
                        editor.commit()
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_signInFragment_to_createPasswordCustomer)

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}