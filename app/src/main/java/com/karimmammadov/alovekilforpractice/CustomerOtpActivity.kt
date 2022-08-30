package com.karimmammadov.alovekilforpractice

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_customer_otp.*
import kotlinx.android.synthetic.main.activity_lawyer_otp.*
import java.util.concurrent.TimeUnit

class CustomerOtpActivity : AppCompatActivity() {

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MAIN_TAG"
    private lateinit var progressDialog: ProgressDialog
    private val collection: Collection<String>? = null
    private val isUsersignedin = FirebaseAuth.getInstance().currentUser
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_otp)

        back_signupcustomer.setOnClickListener {
            val intent = Intent(this@CustomerOtpActivity,ChooseSignUpActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }

        back_signupcustomer.visibility = View.VISIBLE
        tv_numberCustomerHighlight.visibility = View.VISIBLE
        tv_enteryourphonenumber.visibility = View.VISIBLE
        ll_NumberArea.visibility = View.VISIBLE
        btn_sendCstmOtp.visibility = View.VISIBLE

        tv_otpcustomerHighlight.visibility = View.GONE
        tv_enterverificationcode.visibility = View.GONE
        numberCustomerDescription.visibility = View.GONE
        ll_otpArea.visibility = View.GONE
        btn_nextCstmRegister.visibility = View.GONE


        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
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
                Toast.makeText(this@CustomerOtpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
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
                back_signupcustomer.visibility = View.GONE
                tv_numberCustomerHighlight.visibility = View.GONE
                tv_enteryourphonenumber.visibility = View.GONE
                ll_NumberArea.visibility = View.GONE
                btn_sendCstmOtp.visibility = View.GONE

                tv_otpcustomerHighlight.visibility = View.VISIBLE
                tv_enterverificationcode.visibility = View.VISIBLE
                numberCustomerDescription.visibility = View.VISIBLE
                ll_otpArea.visibility = View.VISIBLE
                btn_nextCstmRegister.visibility = View.VISIBLE

                numberCustomerDescription.text = "Code sent to number +994${phoneNumberCustomer.text.toString().trim()}"

                Toast.makeText(
                    this@CustomerOtpActivity,
                    "Verification Code sent...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btn_sendCstmOtp.setOnClickListener {
            val phone = phoneNumberCustomer.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    this@CustomerOtpActivity,
                    "Please enter phone number",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    this@CustomerOtpActivity,
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

        btn_nextCstmRegister.setOnClickListener {
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
            } else {
                Toast.makeText(
                    this@CustomerOtpActivity,
                    "Please enter all numbers",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        inputCstmCode1.addTextChangedListener(object : TextWatcher {
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
        inputCstmCode2.addTextChangedListener(object : TextWatcher {
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
        inputCstmCode3.addTextChangedListener(object : TextWatcher {
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
        inputCstmCode4.addTextChangedListener(object : TextWatcher {
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
        inputCstmCode5.addTextChangedListener(object : TextWatcher {
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
        inputCstmCode6.addTextChangedListener(object : TextWatcher {
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

    }


    private fun startPhoneNumberVerification(phone: String) {
        progressDialog.setMessage("Verifying Phone Number...")
        progressDialog.show()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+994" + phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
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
                                this@CustomerOtpActivity,
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
                Toast.makeText(this@CustomerOtpActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
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
                    val intent = Intent(this@CustomerOtpActivity, CustomerRegstrActivity::class.java)
                    intent.putExtra("phone_number",phone)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@CustomerOtpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

       private fun resendVerificationCode(phone: String, token: PhoneAuthProvider.ForceResendingToken?) {
            progressDialog.setMessage("Resending Code...")
            progressDialog.show()
            Log.d(TAG, "resendVerificationCode: $phone")
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+994" + phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBacks!!)
                .setForceResendingToken(token!!)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
