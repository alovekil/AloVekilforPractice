package com.karimmammadov.alovekilforpractice.SignIn

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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_customer_otp.*
import kotlinx.android.synthetic.main.fragment_customer_otp.view.*
import kotlinx.android.synthetic.main.fragment_otp_for_forget_password.*
import kotlinx.android.synthetic.main.fragment_otp_for_forget_password.view.*
import java.util.concurrent.TimeUnit


class OtpForForgetPassword : Fragment() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_otp_for_forget_password, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        view.back_signupforlogin.setOnClickListener {
            findNavController().navigate(R.id.action_customerOtpFragment_to_chooseSignUpFragment)
        }

        view.back_signupforlogin.visibility = View.VISIBLE
        view.tv_numberLoginHighlight.visibility = View.VISIBLE
        view.tv_enteryourphonenumberlogin.visibility = View.VISIBLE
        view.loginnumberarea.visibility = View.VISIBLE
        view.btn_sendLoginOtp.visibility = View.VISIBLE

        view.tv_numberLoginHighlight.visibility = View.GONE
        view.tv_enterverificationcodeLogin.visibility = View.GONE
        view.numberLogInDescription.visibility = View.GONE
        view.ll_otpLogInArea.visibility = View.GONE
        view.btn_nextLogIn.visibility = View.GONE

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
                view.back_signupforlogin.visibility  = View.GONE
                view.tv_numberLoginHighlight.visibility  = View.GONE
                view.tv_enteryourphonenumberlogin.visibility  = View.GONE
                view.loginnumberarea.visibility  = View.GONE
                view.btn_sendLoginOtp.visibility  = View.GONE

                view.tv_numberLoginHighlight.visibility = View.VISIBLE
                view.tv_enterverificationcodeLogin.visibility = View.VISIBLE
                view.numberLogInDescription.visibility = View.VISIBLE
                view.ll_otpLogInArea.visibility = View.VISIBLE
                view.btn_nextLogIn.visibility = View.VISIBLE

                view.numberLogInDescription.text = "Code sent to number +994${phoneNumberlogin.text.toString().trim()}"

                Toast.makeText(
                    requireContext(),
                    "Verification Code sent...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.btn_sendLoginOtp.setOnClickListener {
            val phone = view.phoneNumberlogin.text.toString().trim()
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
                startPhoneNumberVerification(phone)
                isPhoneNumberExist(phone)
                Log.d(TAG, "onCreateView: ")
            }
        }

        view.btn_nextLogIn.setOnClickListener {
            if (!inputchangepasswordCode1.text.toString().trim().isEmpty() && !inputchangepasswordCode2.text.toString().trim()
                    .isEmpty() && !inputchangepasswordCode3.text.toString().trim().isEmpty() &&
                !inputchangepasswordCode4.text.toString().trim().isEmpty() && !inputchangepasswordCode5.text.toString().trim()
                    .isEmpty() && !inputchangepasswordCode6.text.toString().trim().isEmpty()
            ) {
                var code = inputchangepasswordCode1.text.toString() +
                        inputchangepasswordCode2.text.toString() +
                        inputchangepasswordCode3.text.toString() +
                        inputchangepasswordCode4.text.toString() +
                        inputchangepasswordCode5.text.toString() +
                        inputchangepasswordCode6.text.toString()
                verifyingPhoneNumberWithCode(mVerificationId, code)
                addtoFirestore(phoneNumber)
                editor.putString("forgetpasswordforPhone",phoneNumber).apply()
                editor.putInt("savefragments",R.id.action_splashScreenFragment_to_forgetThePasswordFragment).apply()
                editor.commit()
                findNavController().navigate(R.id.action_otpForForgetPassword_to_forgetThePasswordFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter all numbers",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.inputchangepasswordCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputchangepasswordCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputchangepasswordCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputchangepasswordCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputchangepasswordCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        view.inputchangepasswordCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputchangepasswordCode6.requestFocus()
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
                editor.putString("forgetpassword",phone).apply()
                editor.putInt("savefragments",R.id.action_splashScreenFragment_to_forgetThePasswordFragment).apply()
                editor.commit()
                //findNavController().navigate(R.id.action_customerOtpFragment_to_customerRegisterFragment)
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}