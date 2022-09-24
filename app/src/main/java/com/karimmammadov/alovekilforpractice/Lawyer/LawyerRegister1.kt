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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_lawyer_register1.*
import kotlinx.android.synthetic.main.fragment_lawyer_register1.view.*
import kotlinx.android.synthetic.main.fragment_lawyer_register2.view.*
import java.text.SimpleDateFormat
import java.util.*


class LawyerRegister1 : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tvDatePicker : TextView
    var f: LawyerRegister1? = null
    var layout: ConstraintLayout? = null
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
        viewPager= activity?.findViewById<ViewPager2>(R.id.viewPager)!!
            //val pagerAdapter=LawyerRegister1(this)
        sharedPreferences = requireContext().getSharedPreferences("Myprefs", Context.MODE_PRIVATE)
        editor  =  sharedPreferences.edit()

        // val phoneNumberForLawyer = sharedPreferences.getString("lawyerPhoneNumber","+994....")
        //  editPhoneNumberLawyer.setText(phoneNumberForLawyer)
        view.moveSignupFragment.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_chooseSignUpFragment)
        }
        val completeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_gender)
        val genders = ArrayList<String>()
        genders.add("male")
        genders.add("female")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,genders)
        completeText.setAdapter(adapter)

        /////////////////////////////////////////////////////////////////////
//        if(sharedPreferences.getBoolean("lawyerBack" , false)){
            view?.editlawyerName?.setText(sharedPreferences.getString("userLawyerName" , ""))
            view?.editlawyerSurname?.setText(sharedPreferences.getString("userLawyerSurname" , ""))
            view?.editlawyerFatherName?.setText(sharedPreferences.getString("userLawyerFatherName" , ""))
            view?.dropdown_gender?.setText(sharedPreferences.getString("userLawyerGender" , ""))
            view?.tvDateofBirth?.setText(sharedPreferences.getString("lawyerDateBirth" , ""))
            view?.textInputUniversity?.setText(sharedPreferences.getString("lawyerUniversity" , ""))
            view?.editLawyerEmail?.setText(sharedPreferences.getString("lawyeremail" , ""))
//        }
//////////////////////////////////////////////////////////////////////////////////
/*        val completeTextUniversity = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_universtiy)
        val universities = ArrayList<String>()
        universities.add("BMU")
        universities.add("BDU")
        universities.add("ADA")
        val adapterUniversity = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,universities)
        completeTextUniversity.setAdapter(adapterUniversity)*/

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


        val phoneNumberForLawyer = view.findViewById<TextView>(R.id.editPhoneNumberLawyer)
        phoneNumberForLawyer.text = sharedPreferences.getString("lwynumber","+99455123456")
        view.nextBtn.setOnClickListener {
            if(view.editlawyerName.text.toString().equals("") ||
                view.editlawyerSurname.text.toString().equals("")||
                view.editlawyerFatherName.text.toString().equals("")||
                view.dropdown_gender.text.toString().equals("")||
                view.tvDateofBirth.text.toString().equals("")||
                view.textInputUniversity.text.toString().equals("")||
                view.editLawyerEmail.text.toString().equals("")

            ){
                Toast.makeText(requireContext(), "Please,Enter full information", Toast.LENGTH_LONG).show()

            }else{
                block = true
                val userlawyerName = view.editlawyerName.text.toString().trim()
                val userlawyerSurname = view.editlawyerSurname.text.toString().trim()
                val userlawyerFatherName = view.editlawyerFatherName.text.toString().trim()
                val lawyergender = view.dropdown_gender.text.toString().trim()
                val lawyerdatebirth = view.tvDateofBirth.text.toString().trim()
                val lawyerUniversity = view.textInputUniversity.text.toString().trim()
                val lawyerEmail = view.editLawyerEmail.text.toString().trim()

                val phonelawyer =sharedPreferences.getString("lwynumber","+99455123456").toString()

                if (userlawyerName.isEmpty()){
                    editlawyerName.error = "Name required"
                    editlawyerName.requestFocus()
                    block = false
                }
                if (lawyerUniversity.isEmpty()){
                    textInputUniversity.error = "University required"
                    textInputUniversity.requestFocus()
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

//
//                editor.putString("userLawyerName",userlawyerName).apply()
//                editor.putString("userLawyerSurname",userlawyerSurname).apply()
//                editor.putString("userLawyerFatherName",userlawyerFatherName).apply()
//                editor.putString("userLawyerGender",lawyergender).apply()
//                editor.putString("lawyerDateBirth",lawyerdatebirth).apply()
//                editor.putString("lawyerUniversity",lawyerUniversity).apply()
//                editor.putString("lawyeremail",lawyerEmail).apply()
//                editor.putString("lawyerPhoneNumber",phonelawyer).apply()
//                editor.putBoolean("lawyerBack" , false).apply()
//                editor.commit()

                if(block ){
//                val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
//                ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
//                if(f!!.isHidden){
//                    ft.show(f!!)
//                    layout!!.visibility=View.VISIBLE
//                    nextBtn.setText("HIDE")
//                }
//                else{
//                    ft.hide(f!!)
//                    layout!!.visibility=View.GONE
//                    nextBtn.setText("SHOW")
//
//                }


                    viewPager?.currentItem = 1
            }


//                ft.commit()
            }
        }

        return view
    }

    override fun onPause() {
        block = true
        val userlawyerName = view?.editlawyerName?.text.toString().trim()
        val userlawyerSurname = view?.editlawyerSurname?.text.toString().trim()
        val userlawyerFatherName = view?.editlawyerFatherName?.text.toString().trim()
        val lawyergender = view?.dropdown_gender?.text.toString().trim()
        val lawyerdatebirth = view?.tvDateofBirth?.text.toString().trim()
        val lawyerUniversity = view?.textInputUniversity?.text.toString().trim()
        val lawyerEmail = view?.editLawyerEmail?.text.toString().trim()
        val phoneNumberForLawyer = view?.editPhoneNumberLawyer?.text.toString().trim()

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
        if (lawyergender.isEmpty()){
            dropdown_gender.error = "Gender required"
            dropdown_gender.requestFocus()
            block = false
        }
        if (lawyerUniversity.isEmpty()){
            textInputUniversity.error = "University required"
            textInputUniversity.requestFocus()
            block = false
        }
        if (lawyerdatebirth.isEmpty()){
            tvDateofBirth.error = "Date of birth required"
            tvDateofBirth.requestFocus()
            block = false
        }
        if (phoneNumberForLawyer.isEmpty()){
            editPhoneNumberLawyer.error = "Phone Number required"
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
        editor.putString("lawyerPhoneNumber",phoneNumberForLawyer).apply()
        editor.putBoolean("lawyerBack" , false).apply()
        editor.commit()

        super.onPause()
    }



    private fun updateLable(myCalendar : Calendar){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}
