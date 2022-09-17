package com.karimmammadov.alovekilforpractice

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

import kotlinx.android.synthetic.main.activity_lawyer_otp.*
import java.util.concurrent.TimeUnit

class LawyerOtpActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_lawyer_otp)

        sharedPreferences = this!!.getSharedPreferences("lawyer", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

back_signuplawyer.setOnClickListener {

}
       back_signuplawyer.visibility = View.VISIBLE
        tv_numberLawyerHighlight.visibility = View.VISIBLE
        tv_enteryourphonenumberLawyer.visibility = View.VISIBLE
        ll_NumberLawyerArea.visibility = View.VISIBLE
        btn_sendLwyOtp.visibility = View.VISIBLE

        tv_otpLawyerHighlight.visibility = View.GONE
        tv_enterverificationcodeLawyer.visibility = View.GONE
        numberLawyerDescription.visibility = View.GONE
        ll_otpLawyerArea.visibility = View.GONE
        btn_nextLawyerRegister.visibility = View.GONE

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
                Toast.makeText(this@LawyerOtpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
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
                back_signuplawyer.visibility = View.GONE
                tv_numberLawyerHighlight.visibility = View.GONE
                tv_enteryourphonenumberLawyer.visibility = View.GONE
                ll_NumberLawyerArea.visibility = View.GONE
                btn_sendLwyOtp.visibility = View.GONE

                tv_otpLawyerHighlight.visibility = View.VISIBLE
                tv_enterverificationcodeLawyer.visibility = View.VISIBLE
                numberLawyerDescription.visibility = View.VISIBLE
                ll_otpLawyerArea.visibility = View.VISIBLE
                btn_nextLawyerRegister.visibility = View.VISIBLE

                numberLawyerDescription.text = "Code sent to number +994${phoneNumberLawyer.text.toString().trim()}"
                Toast.makeText(this@LawyerOtpActivity, "Verification Code sent...", Toast.LENGTH_SHORT).show()
            }
        }

        btn_sendLwyOtp.setOnClickListener {
            val phone = phoneNumberLawyer.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this@LawyerOtpActivity, "Please enter phone number", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this@LawyerOtpActivity, "Please enter phone number", Toast.LENGTH_SHORT).show()
            } else {
                //new changes
                phoneNumber = phone
                isPhoneNumberExist(phone)
                Log.d(TAG, "onCreateView: ")
            }
        }

        btn_nextLawyerRegister.setOnClickListener {
            if (!inputLwyCode1.text.toString().trim().isEmpty() && !inputLwyCode2.text.toString().trim()
                    .isEmpty() && !inputLwyCode3.text.toString().trim().isEmpty() &&
                !inputLwyCode4.text.toString().trim().isEmpty() && !inputLwyCode5.text.toString().trim()
                    .isEmpty() && !inputLwyCode6.text.toString().trim().isEmpty()
            ) {
                var code = inputLwyCode1.text.toString() +
                        inputLwyCode2.text.toString() +
                        inputLwyCode3.text.toString() +
                        inputLwyCode4.text.toString() +
                        inputLwyCode5.text.toString() +
                        inputLwyCode6.text.toString()
                verifyingPhoneNumberWithCode(mVerificationId, code)
                addtoFirestore(phoneNumber)
            } else {
                Toast.makeText(this@LawyerOtpActivity, "Please enter all numbers", Toast.LENGTH_SHORT).show()
            }
        }

        inputLwyCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputLwyCode2.requestFocus()

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        inputLwyCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputLwyCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        inputLwyCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputLwyCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        inputLwyCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputLwyCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        inputLwyCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    inputLwyCode6.requestFocus()
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
        fireStore1.collection("LawyerNumbers").whereEqualTo("phoneNumber", phoneNumber).get()
            .addOnSuccessListener { task ->
                val fireStore = Firebase.firestore
                fireStore.collection("LawyerNumbers").whereEqualTo("phoneNumber", phoneNumber).get()
                    .addOnSuccessListener { task ->
                        if (task.isEmpty) {
                            Log.d(TAG, "doIfExists: Send data to FireStore")
                            startPhoneNumberVerification(phoneNumber)
                        } else {
                            Toast.makeText(this@LawyerOtpActivity, "This number is exist", Toast.LENGTH_SHORT).show()
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
        firestore.collection("LawyerNumbers").add(phoneNumbers).addOnSuccessListener {

        }.addOnFailureListener {
            Toast.makeText(this@LawyerOtpActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
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
               editor.putString("lawyerPhoneNumber",phone).apply()
               editor.commit()
                val intent = Intent(this@LawyerOtpActivity,LawyerRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this@LawyerOtpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}