package com.karimmammadov.alovekilforpractice.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.view.*
import java.util.*
import kotlin.collections.ArrayList

class LawyerRegisterPage2 : Fragment() {
    private var selectImage:Uri?=null

    lateinit var selectLanguage: BooleanArray
    var languageList: ArrayList<Int> = ArrayList()
    var languageArray = arrayOf("Azərbaycan","English","Русский")

    lateinit var selectArea : BooleanArray
    var areaList : ArrayList<Int> = ArrayList()
    var areaArray = arrayOf("Maliyyə hüququ","Miqrasiya Hüququ","Nəqliyyat Hüququ","Seçki Hüququ","Sığorta Hüququ","Təhsil Hüququ"
        ,"Vergi Hüququ","Əmək Hüququ","Tibb Hüququ","Vərəsəllik Hüququ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page2, container, false)

        selectLanguage = BooleanArray(languageArray.size)
        val languageTextView = view.findViewById<TextView>(R.id.tv_languages)

        languageTextView.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            builder.setTitle("Select Language")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(languageArray, selectLanguage, {dialog,which,isChecked->
                if(isChecked){
                    languageList.add(which)
                    Collections.sort(languageList)
                }else{
                    languageList.remove(which)
                }
            })

            builder.setPositiveButton("OK"){dialogInterface, which ->
                val stringBuilder = StringBuilder()
                for (j in languageList.indices) {
                    stringBuilder.append(languageArray[languageList.get(j)])
                    if(j!= languageList.size-1){
                        stringBuilder.append(", ")
                    }
                }
                tv_languages.setText(stringBuilder.toString())
            }

            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                dialogInterface.dismiss()
            }

            builder.setNeutralButton("Clear all"){dialogInterface, which ->
                for (j in 0 until selectLanguage.size) {
                    selectLanguage[j] = false
                    languageList.clear()
                    tv_languages.setText("")
                }
            }
            builder.show()
        }

        selectArea = BooleanArray(areaArray.size)
        val areTextView = view.findViewById<TextView>(R.id.tv_areas)
        areTextView.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            builder.setTitle("Select Area")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(areaArray, selectArea, {dialog,which,isChecked->
                if(isChecked){
                        areaList.add(which)
                        Collections.sort(areaList)
                }else{
                    areaList.remove(which)
                }
            })

            builder.setPositiveButton("OK"){dialogInterface, which ->
                val stringBuilder = StringBuilder()
                for (j in areaList.indices) {
                    stringBuilder.append(areaArray[areaList.get(j)])
                    if(j!= areaList.size-1){
                        stringBuilder.append(", ")
                    }
                }
                tv_areas.setText(stringBuilder.toString())
            }

            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                dialogInterface.dismiss()
            }

            builder.setNeutralButton("Clear all"){dialogInterface, which ->
                for (j in 0 until selectArea.size) {
                    selectArea[j] = false
                    areaList.clear()
                    tv_areas.setText("")
                }
            }
            builder.show()
        }

/*
        view.uploadCertificate.setOnClickListener {
            openimagechooser()
        }


 */
        return view
    }
/*
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

 */

 */
}

