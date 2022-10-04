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
import com.karimmammadov.alovekilforpractice.api.forcustomer.RetrofitClient
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import com.karimmammadov.alovekilforpractice.models.forcustomer.CustomerModels
import com.karimmammadov.alovekilforpractice.models.DefaultResponse
import kotlinx.android.synthetic.main.fragment_customer_register.*
import kotlinx.android.synthetic.main.fragment_customer_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegisterFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var customerModels: CustomerModels
    private  var block : Boolean  =true
    lateinit var  customername:String
    lateinit var editor: SharedPreferences.Editor
    lateinit var  customersurname :String
    lateinit var  customeremail:String
    lateinit var  customerpassword :String
    lateinit var customerconfirmpassword :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_customer_register, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)

         editor = sharedPreferences.edit()
        view.bck_signActivity.setOnClickListener {
            findNavController().navigate(R.id.action_customerRegisterFragment_to_chooseSignUpFragment)
        }



        val phonenumberCustomer =  view.findViewById<TextView>(R.id.editphoneNumberCustomer)
        phonenumberCustomer.text = sharedPreferences.getString("csmnumber","+99455494495")

        val customername =  view.findViewById<TextView>(R.id.editFirstName)
        customername.text = sharedPreferences.getString("customername","")

        val customersurname =  view.findViewById<TextView>(R.id.editSecondName)
        customersurname.text = sharedPreferences.getString("customersurname","")

        val customeremail =  view.findViewById<TextView>(R.id.editEmail)
        customeremail.text = sharedPreferences.getString("customeremail","")

        val customerpassword =  view.findViewById<TextView>(R.id.editPassword)
        customerpassword.text = sharedPreferences.getString("customerpassword","")

        val customerconfirmpassword =  view.findViewById<TextView>(R.id.editConfirmPassword)
        customerconfirmpassword.text = sharedPreferences.getString("customerconfirmpassword","")


        view.savebtn.setOnClickListener {
            block =true
            val email = view.editEmail.text.toString().trim()
            val name = view.editFirstName.text.toString().trim()
            val secondName = view.editSecondName.text.toString().trim()
            val password = view.editPassword.text.toString().trim()
            val confirmPassword = view.editConfirmPassword.text.toString().trim()

           val phonecustomer = sharedPreferences.getString("csmnumber","+99455494495").toString()
            if (email.isEmpty()){
                editEmail.error = "Email required"
                editEmail.requestFocus()
                block =false
            }
            if (name.isEmpty()){
                editFirstName.error = "First name required"
                editFirstName.requestFocus()
                block =false
            }
            if (secondName.isEmpty()){
                editSecondName.error = "Second name required"
                editSecondName.requestFocus()
                block =false
            }
            if (password.isEmpty()){
                editPassword.error = "Password required"
                editPassword.requestFocus()
                block =false

            }
            if (confirmPassword.isEmpty()){
                editConfirmPassword.error = "Confirm Password required"
                editConfirmPassword.requestFocus()
                block =false
            }


            customerModels = CustomerModels(email,name,secondName,password,confirmPassword,phonecustomer)

            RetrofitClient.instance.createUser(customerModels)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(requireContext(),response.body()?.response, Toast.LENGTH_SHORT).show()
                        editor.putString("customerName1",name).apply()
                        editor.putString("customerSurname1",secondName).apply()
                        editor.putString("customerEmail1",email).apply()
                        editor.commit()
                        if(block) {
                            findNavController().navigate(R.id.action_customerRegisterFragment_to_createPasswordCustomer)
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
        return view
    }

    override fun onPause() {
        getvalues()
        super.onPause()
    }
    fun getvalues(){
        if(!editFirstName.text?.isEmpty()!!){
            customername=view?.editFirstName?.text.toString().trim()
            editor.putString("customername",customername).apply()
        }
        if(!editSecondName.text?.isEmpty()!!){
            customersurname=view?.editSecondName?.text.toString().trim()
            editor.putString("customersurname",customersurname).apply()
        }
        if(!editEmail.text?.isEmpty()!!){
            customeremail=view?.editEmail?.text.toString().trim()
            editor.putString("customeremail",customeremail).apply()
        }
        if(!editPassword.text?.isEmpty()!!){
            customerpassword=view?.editPassword?.text.toString().trim()
            editor.putString("customerpassword",customerpassword).apply()
        }
        if(!editConfirmPassword.text?.isEmpty()!!){
            customerconfirmpassword=view?.editConfirmPassword?.text.toString().trim()
            editor.putString("customerconfirmpassword",customerconfirmpassword).apply()
        }
        editor.commit()

    }
}