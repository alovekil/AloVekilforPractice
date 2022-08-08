package com.karimmammadov.alovekilforpractice

import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.activity_enter_number.*
import java.util.concurrent.TimeUnit

class EnterNumberActivity : AppCompatActivity() {

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MAIN_TAG"
    private lateinit var progressDialog: ProgressDialog
    private fun Tag() = "MAIN_TAG"
    private val collection: Collection<String>? = null
    private val isUsersignedin = FirebaseAuth.getInstance().currentUser
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_number)

        phoneNumberLl.visibility = View.VISIBLE
        codeLl.visibility = View.GONE
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
                Toast.makeText(this@EnterNumberActivity, "${e.message}", Toast.LENGTH_SHORT).show()
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
                phoneNumberLl.visibility = View.GONE
                codeLl.visibility = View.VISIBLE
                Toast.makeText(
                    this@EnterNumberActivity,
                    "Verification Code sent...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btn_enterNumber.setOnClickListener {
            val phone = phoneEdit.text.toString().trim()

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    this@EnterNumberActivity,
                    "Please enter phone number",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(
                    this@EnterNumberActivity,
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

        btn_enterCode1.setOnClickListener {
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
                addtoFirestore(phoneNumber)
            } else {
                Toast.makeText(
                    this@EnterNumberActivity,
                    "Please enter all numbers",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        inputCode1.addTextChangedListener(object : TextWatcher {
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
        inputCode2.addTextChangedListener(object : TextWatcher {
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
        inputCode3.addTextChangedListener(object : TextWatcher {
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
        inputCode4.addTextChangedListener(object : TextWatcher {
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
        inputCode5.addTextChangedListener(object : TextWatcher {
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
                                this@EnterNumberActivity,
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
                Toast.makeText(this@EnterNumberActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
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

                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@EnterNumberActivity, "${e.message}", Toast.LENGTH_SHORT).show()
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
