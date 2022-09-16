package com.karimmammadov.alovekilforpractice

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AlertDialogActivityforLawyer : AppCompatActivity() {
    var dialog:Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_activityfor_lawyer)
        alertdialogshow()
    }
    private fun alertdialogshow(){
        val dialogview= View.inflate(this,R.layout.activity_alert_dialog_activityfor_lawyer,null)
        val alertDialogBuilder= AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogview)
        dialog=alertDialogBuilder.create()
        dialog!!.show()
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setCancelable(false)
    }
}