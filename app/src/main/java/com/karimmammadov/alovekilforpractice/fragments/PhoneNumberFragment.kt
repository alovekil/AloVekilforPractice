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
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    private val collection:Collection<String>?=null
    private val isUsersignedin=FirebaseAuth.getInstance().currentUser

    private lateinit var phoneNumber : String


=======
    private lateinit var phoneNumber : String

>>>>>>> de4239cd7d05f00eeb957da906de3407252056a5
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
<<<<<<< HEAD

                 if (TextUtils.isEmpty(phone)) {
                     Toast.makeText(this.activity, "Please enter phone number", Toast.LENGTH_SHORT)
                         .show()
                 }


            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this.activity, "Please enter phone number", Toast.LENGTH_SHORT).show()
            }

            else {
                //new changes
                phoneNumber = phone
                isPhoneNumberExist(phone)
                Log.d(TAG, "onCreateView: ")
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
                addtoFirestore(phoneNumber)
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

    private fun   isPhoneNumberExist(phoneNumber:String): Boolean {

        val fireStore1 = Firebase.firestore
        fireStore1.collection("Numbers").whereEqualTo("phoneNumber",phoneNumber).get().addOnSuccessListener { task->

        val fireStore = Firebase.firestore
        fireStore.collection("Numbers").whereEqualTo("phoneNumber",phoneNumber).get().addOnSuccessListener { task->

            if(task.isEmpty){
                Log.d(TAG, "doIfExists: Send data to FireStore")
                startPhoneNumberVerification(phoneNumber)
            }else{
                Toast.makeText(this.activity,"This number is exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Log.d(TAG, "doIfExists: ${it.message}")
        }
        return true
    }

    private fun addtoFirestore(phone:String) {
        val phoneNumbers = hashMapOf(
            "phoneNumber" to phone
        )
        var firestore = Firebase.firestore
        firestore.collection("Numbers").add(phoneNumbers).addOnSuccessListener {

        }.addOnFailureListener {
            Toast.makeText(this.activity,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }
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
                val intent = Intent(this@PhoneNumberFragment.requireContext(), CustomerRegsterActivity::class.java)
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