package com.karimmammadov.alovekilforpractice.Lawyer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karimmammadov.alovekilforpractice.R


class AlertDialogLawyer : Fragment() {
lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_alert_dialog_lawyer, container, false)


        return view
        alertdialogshow()
    }

    private fun alertdialogshow(){
        val dialogview= View.inflate(context,R.layout.arter_dialog,null)
        val alertDialogBuilder= AlertDialog.Builder(context)
        alertDialogBuilder.setView(dialogview)
        dialog=alertDialogBuilder.create()
        dialog.show()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
    }
}