package com.karimmammadov.alovekilforpractice.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_o_t_p_code.*
import kotlinx.android.synthetic.main.fragment_o_t_p_code.view.*
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

class OTPCodeFragment : Fragment() {
    private var forceResendingToken : PhoneAuthProvider.ForceResendingToken? = null
    private var mVerificationId:String?=null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_o_t_p_code, container, false)

        view.inputCode1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode3.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode4.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode5.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })

        view.btn_enterCode.setOnClickListener {
            if (inputCode1.text.toString().trim().isEmpty()
                || inputCode2.text.toString().trim().isEmpty()
                || inputCode3.text.toString().trim().isEmpty()
                || inputCode4.text.toString().trim().isEmpty()
                || inputCode5.text.toString().trim().isEmpty()
                || inputCode6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(this.activity, "Please enter valid code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                var code = inputCode1.text.toString() +
                        inputCode2.text.toString() +
                        inputCode3.text.toString() +
                        inputCode4.text.toString() +
                        inputCode5.text.toString() +
                        inputCode6.text.toString()
            }
           val verificationId =  sharedPreferences?.getString("id",mVerificationId)
            verifyingPhoneNumberWithCode(verificationId, code = "")
        }

        view.textResendOTP.setOnClickListener {
                 resendVerificationCode(phone = "+994",forceResendingToken)
        }
        return view
    }

    private fun verifyingPhoneNumberWithCode(verification:String?,code:String){
        progressDialog.setMessage("Verifying code...")
        progressDialog.show()
        val credential = PhoneAuthProvider.getCredential(verification!!,code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
            val phone= firebaseAuth.currentUser?.phoneNumber
            Toast.makeText(this.activity,"Logged in a ${phone}",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {e->
            progressDialog.dismiss()
            Toast.makeText(this.activity,"${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun resendVerificationCode(phone: String,token: PhoneAuthProvider.ForceResendingToken?){
        progressDialog.setMessage("Resending Code...")
        progressDialog.show()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallBacks!!)
            .setForceResendingToken(token!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}