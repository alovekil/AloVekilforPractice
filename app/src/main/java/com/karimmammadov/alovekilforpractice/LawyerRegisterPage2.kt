package com.karimmammadov.alovekilforpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class LawyerRegisterPage2 : Fragment() {
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

        return view
    }
}