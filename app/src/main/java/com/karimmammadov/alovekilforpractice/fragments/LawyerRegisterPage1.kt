package com.karimmammadov.alovekilforpractice.fragments

import android.app.ActivityOptions
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.ChooseSignUpActivity
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_lawyer_register_page1.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page1.view.*
import java.text.SimpleDateFormat
import java.util.*

class LawyerRegisterPage1 : Fragment() {

    private lateinit var tvDatePicker : TextView
    private lateinit var nextBtn : Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page1, container, false)

        sharedPreferences = requireContext().getSharedPreferences("lawyer",Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        view.bck_signLawyerActivity.setOnClickListener {
            val intent  = Intent(requireContext(),ChooseSignUpActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
        }

        val completeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_gender)
        val genders = ArrayList<String>()
        genders.add("Male")
        genders.add("Female")
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

        nextBtn = view.findViewById(R.id.nextButton)


            val userlawyerName = view.editlawyerName.text.toString().trim()
            val userlawyerSurname = view.editlawyerSurname.text.toString().trim()
            val userlawyerFatherName = view.editlawyerName.text.toString().trim()
            val lawyergender = view.dropdown_gender.text.toString().trim()
            val lawyerdatebirth = view.tvDateofBirth.text.toString().trim()
            val lawyerUniversity = view.dropdown_universtiy.text.toString().trim()
            val lawyerEmail = view.editLawyerEmail.text.toString().trim()

            editor.putString("userLawyerName",userlawyerName).apply()
            editor.putString("userLawyerSurname",userlawyerSurname).apply()
            editor.putString("userLawyerFatherName",userlawyerFatherName).apply()
            editor.putString("userLawyerGender",lawyergender).apply()
            editor.putString("lawyerDateBirth",lawyerdatebirth).apply()
            editor.putString("lawyerUniversity",lawyerUniversity).apply()
            editor.putString("lawyeremail",lawyerEmail).apply()
            editor.commit()

        return view
    }

    private fun updateLable(myCalendar : Calendar){
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}