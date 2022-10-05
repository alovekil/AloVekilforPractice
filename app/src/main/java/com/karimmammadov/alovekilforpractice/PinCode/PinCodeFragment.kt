package com.karimmammadov.alovekilforpractice.PinCode

import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.*
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.karimmammadov.alovekilforpractice.MainActivity
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.constant.MyConstants
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.*
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.cancelTextView
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.circle1
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.circle2
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.circle3
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.circle4
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.deletenumbers
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number0
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number1
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number2
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number3
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number4
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number5
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number6
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number7
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number8
import kotlinx.android.synthetic.main.fragment_create_password_customer.view.number9
import kotlinx.android.synthetic.main.fragment_pin_code.view.*


class PinCodeFragment : Fragment() {
    var gpassword2:String? = ""
    lateinit var editor: SharedPreferences.Editor
    // var binding:ActivityPinCodeBinding?=null
    var password1 = ""
    var dialog: AlertDialog?=null
    var radioList1: ArrayList<RadioButton> = ArrayList()
    var radioList2: ArrayList<RadioButton> = ArrayList()
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var useTouchid : TextView
    private var cancellationSignal : CancellationSignal?=null
    private val authenticationCallback : BiometricPrompt.AuthenticationCallback
        get()=
            @RequiresApi(Build.VERSION_CODES.P)
            object: BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Error message;$errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    val mySharedPreferences = requireContext().getSharedPreferences("Myprefs", 0)
                    val islawyer = mySharedPreferences.getString("usertype","")
                    Log.d(ContentValues.TAG, "onFinish: $islawyer")
                    if (islawyer!!.equals("customer")) {
                        findNavController().navigate(R.id.action_pinCodeFragment_to_profileFragmentCustomer)
                    } else {
                        findNavController().navigate(R.id.action_pinCodeFragment_to_alertDialogLawyer)
                    }
                    Toast.makeText(context,"succes login" , Toast.LENGTH_SHORT).show()
                   /* findNavController().navigate(R.id.action_pinCodeFragment_to_alertDialogLawyer)*/
                    // startActivity(Intent(this@PinCodeActivity, ProfileActivity::class.java))
                    // finish()
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_pin_code, container, false)

        checkBiometricSupport()
        //   binding=DataBindingUtil.inflate(layoutInflater,R.layout.activity_pin_code,null,false)
        sharedPreferences=requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        radioList1.add(view.circle1)
        radioList1.add(view.circle2)
        radioList1.add(view.circle3)
        radioList1.add(view.circle4)
        view.number0.setOnClickListener { view -> passwordCheck("0") }
        view.number1.setOnClickListener { view -> passwordCheck("1") }
        view.number2.setOnClickListener { view -> passwordCheck("2") }
        view.number3.setOnClickListener { view -> passwordCheck("3") }
        view.number4.setOnClickListener { view -> passwordCheck("4") }
        view.number5.setOnClickListener { view -> passwordCheck("5") }
        view.number6.setOnClickListener { view -> passwordCheck("6") }
        view.number7.setOnClickListener { view -> passwordCheck("7") }
        view.number8.setOnClickListener { view -> passwordCheck("8") }
        view.number9.setOnClickListener { view -> passwordCheck("9") }

        view.deletenumbers.setOnClickListener{
            if (password1!!.length > 0) {
                password1 = password1!!.substring(0, password1!!.length - 1)
                radio1True(password1!!.length)
                System.out.println(password1)
            }

        }


        useTouchid = view.useTouchid
        useTouchid.setOnClickListener {
            val biometricPrompt= BiometricPrompt.Builder(activity)
                .setTitle("Title of Prompt")
                .setSubtitle("Authentication is required")
                .setDescription("This app uses fingerprint")
                .setNegativeButton("Cancel",this!!.requireActivity().mainExecutor,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        notifyUser("Authentication is cancelled")
                    }).build()
            biometricPrompt.authenticate(getCancellationSignal(),requireActivity().mainExecutor,authenticationCallback)
        }
        view.cancelTextView.setOnClickListener {
            findNavController().navigate(R.id.action_pinCodeFragment_to_signInUpFragment)
        }

        val handler = Handler()
        val runnable: Runnable = Runnable {
            if (sharedPreferences!!.getBoolean("create_password", false)!!) {
                //   startActivity(Intent(this@PinCodeActivity, ProfileActivity::class.java))
                //  finish()
            } else {
                // startActivity(Intent(this@PinCodeActivity, CreatePasswordActivity::class.java))
                //  finish()
            }
        }
        return view
    }

    private fun radio1True(length: Int) {
        for(i in 0..3 ){
            if (i < length) {
                radioList1[i].isChecked = true
            } else {
                radioList1[i].isChecked = false
            }
        }
    }

    private fun passwordCheck(s: String) {
        if(password1!!.length<4){
            password1+=s
            radio1True(password1.length)
        }

        checkpaswordequal()
    }
    private fun checkpaswordequal() {
        if (password1!!.length==4 ){
            if(password1.equals(sharedPreferences.getString("password","862186214632"))){
                //  Pin_CodeText.setVisibility(View.VISIBLE)
                val mySharedPreferences = requireContext().getSharedPreferences("Myprefs", 0)
                val islawyer = mySharedPreferences.getString("usertype","")
                Log.d(ContentValues.TAG, "onFinish: $islawyer")
                    if (islawyer!!.equals("customer")) {
                        findNavController().navigate(R.id.action_pinCodeFragment_to_profileFragmentCustomer)
                    } else {
                        findNavController().navigate(R.id.action_pinCodeFragment_to_alertDialogLawyer)
                }
                Toast.makeText(context,"succes login" , Toast.LENGTH_SHORT).show()
                //  startActivity(Intent(this@PinCodeActivity, ProfileActivity::class.java))
                //  finish()

            }
            else{
                password1=""
                radio1True(password1.length)
            }
        }
    }
    private fun checkBiometricSupport():Boolean {
        val keyguardmanager=requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardmanager.isKeyguardSecure){
            notifyUser("Fingerprint authentication has not been enabled in Settings")
            return false
        }
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            notifyUser("Fingerprint authentication has not been enabled ")
            return false
        }
        return if(requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }
    private fun notifyUser(message:String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal= CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }



    }


