package com.karimmammadov.alovekilforpractice.SignIn

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.api.forforgetPassword.ApiforgetPassword
import com.karimmammadov.alovekilforpractice.api.forlogin.ApiForLogin
import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetPasswordRequest
import com.karimmammadov.alovekilforpractice.models.forforgetpassword.ForgetpasswordResponse
import com.karimmammadov.alovekilforpractice.models.forlogin.Users
import kotlinx.android.synthetic.main.fragment_customer_otp.view.*
import kotlinx.android.synthetic.main.fragment_customer_register.view.*

import kotlinx.android.synthetic.main.fragment_customer_register.view.savebtn
import kotlinx.android.synthetic.main.fragment_forget_the_password.view.*
import kotlinx.android.synthetic.main.fragment_lawyer_register2.view.*
import kotlinx.android.synthetic.main.fragment_otp_for_forget_password.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class ChangePasswordFragment : Fragment() {
    private lateinit var phoneNumber: String
    lateinit var forgetPasswordRequest: ForgetPasswordRequest
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var forgetpasswordResponse: ForgetpasswordResponse
    private lateinit var editor: SharedPreferences.Editor
    val AUTH = "Basic " + Base64.encodeToString("Kerim:kerim123".toByteArray(), Base64.NO_WRAP)
    private  val BASE_URL = "http://38.242.221.247/api/"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_forget_the_password, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        val phoneNumberForForget = view.findViewById<TextView>(R.id.editPhoneNumberForget)
        phoneNumberForForget.text = sharedPreferences.getString("forgetpasswordforPhone","+99455123456")
        view.savebtnforforget.setOnClickListener {
            val passsword=view.editPasswordforForget.text.toString().trim()
            val confirmpasssword=view.confirmpassword.text.toString().trim()
            val phoneForForget = sharedPreferences.getString("forgetpasswordforPhone","+99455494495").toString()
            if(passsword.isEmpty()){
                view.editPasswordforForget.error="Password required"
                view.editPasswordforForget.requestFocus()
                return@setOnClickListener
            }
            if(confirmpasssword.isEmpty()){
                view.confirmpassword.error="Password required"
                view.confirmpassword.requestFocus()
                return@setOnClickListener
            }
            if(passsword!=confirmpasssword){
                    view.editPasswordforForget.error = "Password and Confirm Password must be same"
                    view.editPasswordforForget.requestFocus()
                    view.confirmpassword.error = "Password and Confirm Password must be same"
                    view.confirmpassword.requestFocus()
                    return@setOnClickListener
            }
            /*val phone = view.phoneNumberlogin.text.toString().trim()*/
            forgetPasswordRequest= ForgetPasswordRequest(phoneForForget,passsword,confirmpasssword)
            sendMyAreasData()
        }

        return view
    }
    private  fun sendMyAreasData() {

        var requestPhone =  RequestBody.create(MediaType.parse("text/plain") , forgetPasswordRequest.phone)
        var reguesUserPassword =  RequestBody.create(MediaType.parse("text/plain") , forgetPasswordRequest.password)
        var reguesUserConfirmPassword =  RequestBody.create(MediaType.parse("text/plain") , forgetPasswordRequest.password2)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiforgetPassword::class.java)
        val retrofitData = retrofitBuilder.resetpassword(requestPhone,reguesUserPassword,reguesUserConfirmPassword,"Token b9279e23f0ecb3249562ab589928ce1666fee13c")

        retrofitData.enqueue(object : Callback<ForgetpasswordResponse> {
                override fun onResponse(
                    call: Call<ForgetpasswordResponse>,
                    response: Response<ForgetpasswordResponse>
                ) {

                    if(response.body()?.code == 200){
                        editor.putString("tokenvalue", response.body()!!.user_token)
                        editor.putString("usertype",response.body()!!.status)
                        /*editor.putInt("code",response.body()!!.mes)*/
                        editor.commit()
                        Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_forgetThePasswordFragment_to_createPasswordCustomer)

                    }

                }

                override fun onFailure(call: Call<ForgetpasswordResponse>, t: Throwable) {
                    println(t)
//                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }


            })

}
}