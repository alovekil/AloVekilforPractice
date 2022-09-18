package com.karimmammadov.alovekilforpractice.Lawyer

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_lawyer_register1.*
import kotlinx.android.synthetic.main.fragment_lawyer_register1.view.*
import java.text.SimpleDateFormat
import java.util.*


class LawyerRegister1 : Fragment() {

    private lateinit var tvDatePicker : TextView
    private lateinit var nextBtn : Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private  var block : Boolean  =true
    // private var phoneNumberForLawyer:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register1, container, false)

        sharedPreferences = requireContext().getSharedPreferences("lawyer", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        // val phoneNumberForLawyer = sharedPreferences.getString("lawyerPhoneNumber","+994....")
        //  editPhoneNumberLawyer.setText(phoneNumberForLawyer)
        view.moveSignupFragment.setOnClickListener {
            findNavController().navigate(R.id.action_lawyerRegister1_to_chooseSignUpFragment)
        }
        val completeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_gender)
        val genders = ArrayList<String>()
        genders.add("male")
        genders.add("female")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,genders)
        completeText.setAdapter(adapter)

        val completeTextUniversity = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_universtiy)
        val universities = ArrayList<String>()
        universities.add("BMU")
        universities.add("BDU")
        universities.add("ADA")
        val adapterUniversity = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,universities)
        completeTextUniversity.setAdapter(adapterUniversity)

        tvDatePicker = view.findViewById(R.id.tvDateofBirth)
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        tvDatePicker.setOnClickListener {
            DatePickerDialog(requireContext(),datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }



        view.nextBtn.setOnClickListener {
            block = true
            val userlawyerName = view.editlawyerName.text.toString().trim()
            val userlawyerSurname = view.editlawyerSurname.text.toString().trim()
            val userlawyerFatherName = view.editlawyerFatherName.text.toString().trim()
            val lawyergender = view.dropdown_gender.text.toString().trim()
            val lawyerdatebirth = view.tvDateofBirth.text.toString().trim()
            val lawyerUniversity = view.dropdown_universtiy.text.toString().trim()
            val lawyerEmail = view.editLawyerEmail.text.toString().trim()
            val phoneNumberForLawyer = view.findViewById<TextView>(R.id.editPhoneNumberLawyer)
            phoneNumberForLawyer.text = sharedPreferences.getString("lwynumber","+99455123456")
            val phonelawyer = phoneNumberForLawyer.toString().trim()

            if (userlawyerName.isEmpty()){
                editlawyerName.error = "Name required"
                editlawyerName.requestFocus()
                block = false
            }
            if (userlawyerSurname.isEmpty()){
                editlawyerSurname.error = "Surname required"
                editlawyerSurname.requestFocus()
                block = false
            }
            if (userlawyerFatherName.isEmpty()){
                editlawyerFatherName.error = "Father name required"
                editlawyerFatherName.requestFocus()
                block = false
            }
            if (lawyerEmail.isEmpty()){
                editLawyerEmail.error = "Email required"
                editLawyerEmail.requestFocus()
                block = false
            }
            if (phonelawyer.isEmpty()){
                editPhoneNumberLawyer.error = "Phone required"
                editPhoneNumberLawyer.requestFocus()
                block = false
            }


            editor.putString("userLawyerName",userlawyerName).apply()
            editor.putString("userLawyerSurname",userlawyerSurname).apply()
            editor.putString("userLawyerFatherName",userlawyerFatherName).apply()
            editor.putString("userLawyerGender",lawyergender).apply()
            editor.putString("lawyerDateBirth",lawyerdatebirth).apply()
            editor.putString("lawyerUniversity",lawyerUniversity).apply()
            editor.putString("lawyeremail",lawyerEmail).apply()
            editor.putString("lawyerPhoneNumber",phonelawyer).apply()
            editor.commit()

            if(block ){
            findNavController().navigate(R.id.action_lawyerRegister1_to_lawyerRegister2)
        }
        }

        return view
    }
/*
    override fun onPause() {

        val userlawyerName = view!!.editlawyerName.text.toString().trim()
        val userlawyerSurname = view!!.editlawyerSurname.text.toString().trim()
        val userlawyerFatherName = view!!.editlawyerFatherName.text.toString().trim()
        val lawyergender = view!!.dropdown_gender.text.toString().trim()
        val lawyerdatebirth = view!!.tvDateofBirth.text.toString().trim()
        val lawyerUniversity = view!!.dropdown_universtiy.text.toString().trim()
        val lawyerEmail = view!!.editLawyerEmail.text.toString().trim()
        val phoneNumberForLawyer = view!!.editPhoneNumberLawyer.text.toString().trim()

        if (userlawyerName.isEmpty()){
            editlawyerName.error = "Name required"
            editlawyerName.requestFocus()
        }
        if (userlawyerSurname.isEmpty()){
            editlawyerSurname.error = "Surname required"
            editlawyerSurname.requestFocus()
        }
        if (userlawyerFatherName.isEmpty()){
            editlawyerFatherName.error = "Father name required"
            editlawyerFatherName.requestFocus()
        }
        if (lawyerEmail.isEmpty()){
            editLawyerEmail.error = "Email required"
            editLawyerEmail.requestFocus()
        }
        if (lawyergender.isEmpty()){
            dropdown_gender.error = "Gender required"
            dropdown_gender.requestFocus()
        }
        if (lawyerUniversity.isEmpty()){
            dropdown_universtiy.error = "University required"
            dropdown_universtiy.requestFocus()
        }
        if (lawyerdatebirth.isEmpty()){
            tvDateofBirth.error = "Date of birth required"
            tvDateofBirth.requestFocus()
        }
        if (phoneNumberForLawyer.isEmpty()){
            editPhoneNumberLawyer.error = "Phone Number required"
            editPhoneNumberLawyer.requestFocus()
        }

        editor.putString("userLawyerName",userlawyerName).apply()
        editor.putString("userLawyerSurname",userlawyerSurname).apply()
        editor.putString("userLawyerFatherName",userlawyerFatherName).apply()
        editor.putString("userLawyerGender",lawyergender).apply()
        editor.putString("lawyerDateBirth",lawyerdatebirth).apply()
        editor.putString("lawyerUniversity",lawyerUniversity).apply()
        editor.putString("lawyeremail",lawyerEmail).apply()
        editor.putString("lawyerPhoneNumber",phoneNumberForLawyer).apply()
        editor.commit()

        super.onPause()
    }

 */
    private fun updateLable(myCalendar : Calendar){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}