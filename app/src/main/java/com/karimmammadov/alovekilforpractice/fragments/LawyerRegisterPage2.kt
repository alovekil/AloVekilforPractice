package com.karimmammadov.alovekilforpractice.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.view.*

class LawyerRegisterPage2 : Fragment() {
    private var selectImage:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page2, container, false)

        val completeTextLanguage = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_language)
        val languages = ArrayList<String>()
        languages.add("Azərbaycan")
        languages.add("English")
        languages.add("Русский")
        val adapterlanguage = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,languages)
        completeTextLanguage.setAdapter(adapterlanguage)

        val completeTextArea = view.findViewById<MaterialAutoCompleteTextView>(R.id.dropdown_area)
        val areas = ArrayList<String>()
        areas.add("Məhkəmə")
        areas.add("Ədliyyə")
        areas.add("Prokurorluq")
        val adapterArea = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,areas)
        completeTextArea.setAdapter(adapterArea)


        view.uploadCertificate.setOnClickListener {
            openimagechooser()
        }

        return view
    }

    private fun openimagechooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type="image/*"
            val mimiTypes= arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,mimiTypes)
            startActivityForResult(it,REQUEST_CODE_IMAGE_PICKER)//it our intent
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK){
            //if user select image
            when(requestCode){
                REQUEST_CODE_IMAGE_PICKER->{
                    selectImage=data?.data //this is give to URI
                    uploadCertificate.setImageURI(selectImage)
                }
            }

        }
    }
    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER=100
    }
}