package com.karimmammadov.alovekilforpractice.customer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.api.RetrofitClient
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import com.karimmammadov.alovekilforpractice.constant.UserNumbers
import com.karimmammadov.alovekilforpractice.models.CustomerModels
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.fragment_customer_register.*
import kotlinx.android.synthetic.main.fragment_customer_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegisterFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var customerModels: CustomerModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_customer_register, container, false)

        sharedPreferences = requireContext().getSharedPreferences("customer", Context.MODE_PRIVATE)

        view.bck_signActivity.setOnClickListener {
            findNavController().navigate(R.id.action_customerRegisterFragment_to_chooseSignUpFragment)
        }

        val loginSharedPreferences = requireContext().getSharedPreferences("Myprefs",0)
        val editor = loginSharedPreferences.edit()

        view.savebtn.setOnClickListener {
            val email = view.editEmail.text.toString().trim()
            val name = view.editFirstName.text.toString().trim()
            val secondName = view.editSecondName.text.toString().trim()
            val password = view.editPassword.text.toString().trim()
            val confirmPassword = view.editConfirmPassword.text.toString().trim()
            val phonenumberCustomer =  view.findViewById<TextView>(R.id.editphoneNumberCustomer)
            phonenumberCustomer.text = arguments?.getString("csnumber")
            val phonecustomer = phonenumberCustomer.toString().trim()
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


            customerModels = CustomerModels(email,name,secondName,password,confirmPassword,phonecustomer)

            RetrofitClient.instance.createUser(customerModels)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(requireContext(),response.body()?.response, Toast.LENGTH_SHORT).show()
                        editor.putString(MyConstants.userName,name)
                        editor.putString(MyConstants.userSecondName,secondName)
                        editor.putString(MyConstants.userEmail,email)
                        editor.putBoolean(MyConstants.args,true)
                        editor.commit()
                     findNavController().navigate(R.id.action_customerRegisterFragment_to_createPasswordCustomer)

                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
        return view
    }
}