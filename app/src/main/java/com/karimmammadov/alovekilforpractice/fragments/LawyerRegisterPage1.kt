package com.karimmammadov.alovekilforpractice.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LawyerRegisterPage1 : Fragment() {

    private lateinit var tvDatePicker : TextView
    private lateinit var nextBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page1, container, false)
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

        return view
    }

    private fun updateLable(myCalendar : Calendar){
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}