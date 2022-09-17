package com.karimmammadov.alovekilforpractice.PinCode

import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.karimmammadov.alovekilforpractice.MainActivity
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.activity_pin_code.cancelTextView
import kotlinx.android.synthetic.main.activity_pin_code.circle1
import kotlinx.android.synthetic.main.activity_pin_code.circle2
import kotlinx.android.synthetic.main.activity_pin_code.circle3
import kotlinx.android.synthetic.main.activity_pin_code.circle4
import kotlinx.android.synthetic.main.activity_pin_code.deletenumbers
import kotlinx.android.synthetic.main.activity_pin_code.number0
import kotlinx.android.synthetic.main.activity_pin_code.number1
import kotlinx.android.synthetic.main.activity_pin_code.number2
import kotlinx.android.synthetic.main.activity_pin_code.number3
import kotlinx.android.synthetic.main.activity_pin_code.number4
import kotlinx.android.synthetic.main.activity_pin_code.number5
import kotlinx.android.synthetic.main.activity_pin_code.number6
import kotlinx.android.synthetic.main.activity_pin_code.number7
import kotlinx.android.synthetic.main.activity_pin_code.number8
import kotlinx.android.synthetic.main.activity_pin_code.number9



class PinCodeActivity : AppCompatActivity() {
    var gpassword2:String? = ""
    lateinit var editor: SharedPreferences.Editor
   // var binding:ActivityPinCodeBinding?=null
    var password1 = ""
    var dialog:AlertDialog ?=null
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
                   // startActivity(Intent(this@PinCodeActivity, ProfileActivity::class.java))
                   // finish()
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        checkBiometricSupport()
     //   binding=DataBindingUtil.inflate(layoutInflater,R.layout.activity_pin_code,null,false)
        sharedPreferences=this!!.getSharedPreferences("password", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        radioList1.add(circle1);
        radioList1.add(circle2);
        radioList1.add(circle3);
        radioList1.add(circle4);
        number0.setOnClickListener { view -> passwordCheck("0") }
        number1.setOnClickListener { view -> passwordCheck("1") }
      number2.setOnClickListener { view -> passwordCheck("2") }
        number3.setOnClickListener { view -> passwordCheck("3") }
       number4.setOnClickListener { view -> passwordCheck("4") }
        number5.setOnClickListener { view -> passwordCheck("5") }
      number6.setOnClickListener { view -> passwordCheck("6") }
        number7.setOnClickListener { view -> passwordCheck("7") }
       number8.setOnClickListener { view -> passwordCheck("8") }
        number9.setOnClickListener { view -> passwordCheck("9") }
        alertdialogshow()
        deletenumbers.setOnClickListener{
            if (password1!!.length > 0) {
                password1 = password1!!.substring(0, password1!!.length - 1)
                radio1True(password1!!.length)
                System.out.println(password1)
            }
  
        }


        useTouchid = findViewById(R.id.useTouchid)
        useTouchid.setOnClickListener {
            val biometricPrompt= BiometricPrompt.Builder(this)
                .setTitle("Title of Prompt")
                .setSubtitle("Authentication is required")
                .setDescription("This app uses fingerprint")
                .setNegativeButton("Cancel",this.mainExecutor,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        notifyUser("Authentication is cancelled")
                    }).build()
            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCallback)
        }
        cancelTextView.setOnClickListener {
            startActivity(Intent(this@PinCodeActivity, MainActivity::class.java))
            finish()
        }

        val handler = Handler()
        val runnable: Runnable = Runnable {
            if (sharedPreferences!!.getBoolean("create_pasword", false)!!) {
             //   startActivity(Intent(this@PinCodeActivity, ProfileActivity::class.java))
              //  finish()
            } else {
               // startActivity(Intent(this@PinCodeActivity, CreatePasswordActivity::class.java))
              //  finish()
            }
        }

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
                Toast.makeText(this,"succes login" ,Toast.LENGTH_SHORT).show()
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
        val keyguardmanager=getSystemService(Context.KEYGUARD_SERVICE)as KeyguardManager
        if(!keyguardmanager.isKeyguardSecure){
            notifyUser("Fingerprint authentication has not been enabled in Settings")
            return false
        }
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            notifyUser("Fingerprint authentication has not been enabled ")
            return false
        }
        return if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }

    private fun notifyUser(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal= CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }
    private fun alertdialogshow(){
    val dialogview= View.inflate(this@PinCodeActivity,R.layout.arter_dialog,null)
        val alertDialogBuilder=AlertDialog.Builder(this@PinCodeActivity)
        alertDialogBuilder.setView(dialogview)
        dialog=alertDialogBuilder.create()
        dialog!!.show()
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setCancelable(false)
    }

}