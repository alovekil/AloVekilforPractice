package com.karimmammadov.alovekilforpractice.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.karimmammadov.alovekilforpractice.CustomerRegisterActivity
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode1
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode2
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode3
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode4
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode5
import kotlinx.android.synthetic.main.fragment_phone_number.inputCode6
import kotlinx.android.synthetic.main.fragment_phone_number.phoneEdit
import kotlinx.android.synthetic.main.fragment_phone_number.view.*
import kotlinx.android.synthetic.main.fragment_phone_number.view.btn_enterNumber
import kotlinx.android.synthetic.main.fragment_phone_number.view.codeLl
import kotlinx.android.synthetic.main.fragment_phone_number.view.inputCode1
import kotlinx.android.synthetic.main.fragment_phone_number.view.inputCode2
import kotlinx.android.synthetic.main.fragment_phone_number.view.inputCode3
import kotlinx.android.synthetic.main.fragment_phone_number.view.inputCode4
import kotlinx.android.synthetic.main.fragment_phone_number.view.inputCode5
import kotlinx.android.synthetic.main.fragment_phone_number.view.phoneNumberLl
import kotlinx.android.synthetic.main.fragment_phone_number.view.textResendOTP
import java.util.concurrent.TimeUnit


class PhoneNumberFragment : Fragment() {

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MAIN_TAG"
    private lateinit var progressDialog: ProgressDialog
    private  fun Tag() = "MAIN_TAG"
    private val isUsersignedin=FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_number, container, false)


        view.phoneNumberLl.visibility = View.VISIBLE
        view.codeLl.visibility = View.GONE
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)


        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: ")
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }
            override fun onVerificationFailed(e: FirebaseException) {
                progressDialog.dismiss()
                Log.d(TAG, "onVerificationFailed:${e.message} ")
                Toast.makeText(activity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                mVerificationId = verificationId
                forceResendingToken = token
                progressDialog.dismiss()
                Log.d(TAG, "onCodeSent:$verificationId")
                view.phoneNumberLl.visibility = View.GONE
                view.codeLl.visibility = View.VISIBLE
                Toast.makeText(activity, "Verification Code sent...", Toast.LENGTH_SHORT).show()
            }
        }
        view.btn_enterNumber.setOnClickListener{
            val phone = phoneEdit.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this.activity, "Please enter phone number", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if(isUsersignedin!=null){
                    Toast.makeText(this.activity, "Please enter another number . This number has already entered", Toast.LENGTH_SHORT)
                        .show()
                }
                startPhoneNumberVerification(phone)
            }

        }

        view.textResendOTP.setOnClickListener {
            val phone = phoneEdit.text.toString().trim()
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this.activity,"Please enter phone number",Toast.LENGTH_SHORT).show()
            }else{
                resendVerificationCode(phone,forceResendingToken)
            }
        }

       view.btn_enterCode1.setOnClickListener {
            if (!inputCode1.text.toString().trim().isEmpty() && !inputCode2.text.toString().trim()
                    .isEmpty() && !inputCode3.text.toString().trim().isEmpty() &&
                !inputCode4.text.toString().trim().isEmpty() && !inputCode5.text.toString().trim()
                    .isEmpty() && !inputCode6.text.toString().trim().isEmpty()
            ) {
                var code = inputCode1.text.toString() +
                        inputCode2.text.toString() +
                        inputCode3.text.toString() +
                        inputCode4.text.toString() +
                        inputCode5.text.toString() +
                        inputCode6.text.toString()
                verifyingPhoneNumberWithCode(mVerificationId, code)
            } else {
                Toast.makeText(this.activity, "Please enter all numbers", Toast.LENGTH_SHORT).show()
            }
        }

        view.inputCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return view
    }

    private fun startPhoneNumberVerification(phone: String) {
        progressDialog.setMessage("Verifying Phone Number...")
        progressDialog.show()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+994" + phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallBacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyingPhoneNumberWithCode(verificationId: String?, code: String) {
        progressDialog.setMessage("Verifying Code...")
        progressDialog.show()
        var credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage("Logging in")
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val phone = firebaseAuth.currentUser?.phoneNumber
                val intent = Intent(this@PhoneNumberFragment.requireContext(), CustomerRegisterActivity::class.java)
                intent.putExtra("phone_number",phone)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(activity,"${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun resendVerificationCode(phone: String,token: PhoneAuthProvider.ForceResendingToken?){
        progressDialog.setMessage("Resending Code...")
        progressDialog.show()
        Log.d(TAG,"resendVerificationCode: $phone")
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+994" + phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallBacks!!)
            .setForceResendingToken(token!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}