package com.karimmammadov.alovekilforpractice

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_pin_code.*

class PinCodeActivity : AppCompatActivity() {

    private lateinit var useTouchid : LinearLayout
    private var cancellationSignal: CancellationSignal?=null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get()=
            @RequiresApi(Build.VERSION_CODES.P)
            object: BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Error message;$errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@PinCodeActivity,ProfileActivity::class.java))
                    finish()
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        checkBiometricSupport()

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
}