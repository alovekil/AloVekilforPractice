package com.karimmammadov.alovekilforpractice.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
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
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_o_t_p_code.view.*
import kotlinx.android.synthetic.main.fragment_phone_number.*
import kotlinx.android.synthetic.main.fragment_phone_number.view.*
import java.util.concurrent.TimeUnit


class PhoneNumberFragment : Fragment() {

    private  var forceResendingToken: PhoneAuthProvider.ForceResendingToken?=null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? =null
    private var mVerificationId:String?=null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private  fun Tag() = "MAIN_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // dropDownNumberMenu()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_phone_number, container, false)
        val sharedPreferences=this.activity?.getSharedPreferences("com.karimmammadov.alovekilforpractice.fragments", MODE_PRIVATE)
        firebaseAuth= FirebaseAuth.getInstance()
        progressDialog= ProgressDialog(activity)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        mCallBacks=object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d(Tag(),"onverficationcompleted: ")

            }
            override fun onVerificationFailed(p0: FirebaseException) {
                progressDialog.dismiss()
                Log.d(Tag(),"onVerficationfailed ${p0}")
                Toast.makeText(activity,"${p0.message}",Toast.LENGTH_SHORT).show()
            }
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.d(Tag(),"onCodesent:${p0}")
                mVerificationId=p0

                forceResendingToken=p1

                sharedPreferences?.edit()?.putString("id",p0)
                progressDialog.dismiss()
                Toast.makeText(activity,"Verification Code sent...",Toast.LENGTH_SHORT).show()
                val fragmentManager = getFragmentManager()
                val fragmentTransaction = fragmentManager?.beginTransaction()
                val fragmentNumber = OTPCodeFragment()
                fragmentTransaction?.replace(R.id.frameLayout, fragmentNumber)?.commit()
                super.onCodeSent(p0, p1)
            }
        }

        view.btn_enterNumber.setOnClickListener{
            //input phone number
            val phone=phoneEdit.text.toString().trim()
            //validate phone number
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this.activity,"Please enter your phone number...",Toast.LENGTH_SHORT).show()
            }
            else{
                startPhoneNumberVerification(phone)
            }
        }
        return view
    }

    private fun startPhoneNumberVerification(phone: String){
        progressDialog.setMessage("Verifiying Phone Number...")
        progressDialog.show()
        val option= PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallBacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }
}


/*
    private fun dropDownNumberMenu(){
        val popupMenu = PopupMenu(this.activity,btn_numberFilter)
        popupMenu.inflate(R.menu.popup_numberfiltermenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.number1 ->{
                    btn_numberFilter.setText("050")
                    true
                }
                R.id.number2 ->{
                    btn_numberFilter.setText("051")
                    true
                }
                R.id.number3 ->{
                    btn_numberFilter.setText("055")
                    true
                }
                R.id.number4 ->{
                    btn_numberFilter.setText("070")
                    true
                }
                R.id.number5 ->{
                    btn_numberFilter.setText("077")
                    true
                }
                else -> true
            }
        }
    }
 */