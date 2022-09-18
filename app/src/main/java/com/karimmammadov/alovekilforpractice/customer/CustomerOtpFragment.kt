package com.karimmammadov.alovekilforpractice.customer

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.constant.UserNumbers
import kotlinx.android.synthetic.main.fragment_customer_otp.*
import kotlinx.android.synthetic.main.fragment_customer_otp.view.*
import java.util.concurrent.TimeUnit

class CustomerOtpFragment : Fragment() {

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MAIN_TAG"
    private lateinit var progressDialog: ProgressDialog
    private val collection: Collection<String>? = null
    private val isUsersignedin = FirebaseAuth.getInstance().currentUser
    private lateinit var phoneNumber: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_customer_otp, container, false)

        sharedPreferences = requireContext().getSharedPreferences("customer", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        view.back_signupcustomer.setOnClickListener {
            findNavController().navigate(R.id.action_customerOtpFragment_to_chooseSignUpFragment)
        }

        view.back_signupcustomer.visibility = View.VISIBLE
        view.tv_numberCustomerHighlight.visibility = View.VISIBLE
        view.tv_enteryourphonenumber.visibility = View.VISIBLE
        view.ll_NumberArea.visibility = View.VISIBLE
        view.btn_sendCstmOtp.visibility = View.VISIBLE

        view.tv_otpcustomerHighlight.visibility = View.GONE
        view.tv_enterverificationcode.visibility = View.GONE
        view.numberCustomerDescription.visibility = View.GONE
        view.ll_otpArea.visibility = View.GONE
        view.btn_nextCstmRegister.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(requireContext())
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
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
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
                view.back_signupcustomer.visibility = View.GONE
                view.tv_numberCustomerHighlight.visibility = View.GONE
                view.tv_enteryourphonenumber.visibility = View.GONE
                view.ll_NumberArea.visibility = View.GONE
                view.btn_sendCstmOtp.visibility = View.GONE

                view.tv_otpcustomerHighlight.visibility = View.VISIBLE
                view.tv_enterverificationcode.visibility = View.VISIBLE
                view.numberCustomerDescription.visibility = View.VISIBLE
                view.ll_otpArea.visibility = View.VISIBLE
                view.btn_nextCstmRegister.visibility = View.VISIBLE

                view.numberCustomerDescription.text = "Code sent to number +994${phoneNumberCustomer.text.toString().trim()}"

                Toast.makeText(
                    requireContext(),
                    "Verification Code sent...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.btn_sendCstmOtp.setOnClickListener {
            val phone = phoneNumberCustomer.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    requireContext(),
                    "Please enter phone number",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    requireContext(),
                    "Please enter phone number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //new changes
                phoneNumber = phone
                isPhoneNumberExist(phone)
                Log.d(TAG, "onCreateView: ")
            }
        }

        view.btn_nextCstmRegister.setOnClickListener {
            if (!inputCstmCode1.text.toString().trim().isEmpty() && !inputCstmCode2.text.toString().trim()
                    .isEmpty() && !inputCstmCode3.text.toString().trim().isEmpty() &&
                !inputCstmCode4.text.toString().trim().isEmpty() && !inputCstmCode5.text.toString().trim()
                    .isEmpty() && !inputCstmCode6.text.toString().trim().isEmpty()
            ) {
                var code = inputCstmCode1.text.toString() +
                        inputCstmCode2.text.toString() +
                        inputCstmCode3.text.toString() +
                        inputCstmCode4.text.toString() +
                        inputCstmCode5.text.toString() +
                        inputCstmCode6.text.toString()
                verifyingPhoneNumberWithCode(mVerificationId, code)
                addtoFirestore(phoneNumber)
                editor.putString("csmnumber",phoneNumber).apply()
                editor.commit()
                findNavController().navigate(R.id.action_customerOtpFragment_to_customerRegisterFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter all numbers",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.inputCstmCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCstmCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCstmCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCstmCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCstmCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputCstmCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputCstmCode6.requestFocus()
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

    private fun isPhoneNumberExist(phoneNumber: String): Boolean {

        val fireStore1 = Firebase.firestore
        fireStore1.collection("Numbers").whereEqualTo("phoneNumber", phoneNumber).get()
            .addOnSuccessListener { task ->

                val fireStore = Firebase.firestore
                fireStore.collection("Numbers").whereEqualTo("phoneNumber", phoneNumber).get()
                    .addOnSuccessListener { task ->

                        if (task.isEmpty) {
                            Log.d(TAG, "doIfExists: Send data to FireStore")
                            startPhoneNumberVerification(phoneNumber)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "This number is exist",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
                        Log.d(TAG, "doIfExists: ${it.message}")
                    }

            }
        return true
    }

    fun addtoFirestore(phone: String) {
        val phoneNumbers = hashMapOf(
            "phoneNumber" to phone
        )
        var firestore = Firebase.firestore
        firestore.collection("Numbers").add(phoneNumbers).addOnSuccessListener {

        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }


    private  fun verifyingPhoneNumberWithCode(verificationId: String?, code: String) {
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
                editor.putString("csmnumber",phone).apply()
                editor.commit()
                findNavController().navigate(R.id.action_customerOtpFragment_to_customerRegisterFragment)
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}