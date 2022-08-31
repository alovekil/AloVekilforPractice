package com.karimmammadov.alovekilforpractice.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxItemsAdapter
import com.karimmammadov.alovekilforpractice.api.ApiForCustomer
import com.karimmammadov.alovekilforpractice.api.RetrofitClientForLawyer
import com.karimmammadov.alovekilforpractice.models.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LawyerRegisterPage2 : Fragment() {
    val BASE_URL = "http://38.242.221.247/api/"
    lateinit var myCheckBoxItemsAdapter: MyCheckBoxItemsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var lawyer : Lawyer
    val lawyerLanguages = ArrayList<Int>()
    lateinit var lawyerModels: LawyerModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page2, container, false)

        sharedPreferences = requireContext().getSharedPreferences("lawyer", Context.MODE_PRIVATE)
        val languageTextView = view.findViewById<TextView>(R.id.tv_languages)
        languageTextView.setOnClickListener {
            GetManageInstance.removeAllItems()
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.layout_spinner, null, false)
            builder.setView(view)
            val builderCreate = builder.create()
            builderCreate.show()
            val recyclerView = view.findViewById<RecyclerView>(R.id.language_rcyvw)
            var languageList = listOf(
                LawyerLanguageItems(1, "AZE"),
                LawyerLanguageItems(2, "ENG"),
                LawyerLanguageItems(3, "RUS"),
                LawyerLanguageItems(4, "Portuguese")
            )
            myCheckBoxItemsAdapter = MyCheckBoxItemsAdapter(requireContext(), languageList)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = myCheckBoxItemsAdapter
            view.findViewById<TextView>(R.id.tv_clearAll).setOnClickListener {
                myCheckBoxItemsAdapter.notifyItemRangeChanged(0, languageList.size )
                GetManageInstance.removeAllItems()
            }
            view.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                builderCreate.dismiss()
                GetManageInstance.removeAllItems()
            }



            view.findViewById<TextView>(R.id.tv_Ok).setOnClickListener {
                val stringBuilder = StringBuilder()
                builderCreate.dismiss()
                val selectedLanguage = GetManageInstance.getLanguage()
                stringBuilder.append(selectedLanguage.get(0).language)
                for(l in 1..selectedLanguage.size-1){
                    lawyerLanguages.add(selectedLanguage.get(l).id)
                    stringBuilder.append(", ${selectedLanguage.get(l).language}")
                }
                languageTextView.setText(stringBuilder)
            }
        }
        getMyData()

        val listCertificate = ArrayList<String>()
        listCertificate.add("Lawyer Certificate")
        val lglexperience = editLegalExperience.text.toString().trim()
        val diploma = editDiplomaLawyer.text.toString().trim()
        val lawyerExperience = editLawyerExperience.text.toString().trim()
       val serviceTypesLawyers = ArrayList<Int>()
        serviceTypesLawyers.add(4)
        serviceTypesLawyers.add(7)
        val lawyerTaxVoen = lawyerVoen.text.toString().trim()

        lawyer.birth_date = sharedPreferences.getString("lawyerDateBirth",null).toString()
        lawyer.certificate = listCertificate
        lawyer.father_name = sharedPreferences.getString("userLawyerFatherName",null).toString()
        lawyer.gender = sharedPreferences.getString("userLawyerGender","male").toString()
        lawyer.law_practice = lglexperience
        lawyer.lawyer_practice = lawyerExperience
        lawyer.lawyer_card = "Lawyer Card"
        lawyer.service_languages = lawyerLanguages
        lawyer.service_types = serviceTypesLawyers
        lawyer.university = sharedPreferences.getString("lawyerUniversity","BMU").toString()
        lawyer.voen = lawyerTaxVoen

        lawyerModels.lawyer = lawyer

        lawyerModels.email = sharedPreferences.getString("lawyeremail",null).toString()
        lawyerModels.first_name = sharedPreferences.getString("userLawyerName",null).toString()
        lawyerModels.last_name = sharedPreferences.getString("userLawyerSurname",null).toString()
        lawyerModels.phone = sharedPreferences.getString("lawyerPhoneNumber",null).toString()
        lawyerModels.password = "lawyer123"
        lawyerModels.password2 = "lawyer123"

        RetrofitClientForLawyer.instance.createUserLawyer(lawyerModels).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(requireContext(),response.body()?.response, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT).show()
            }

        })

        return view
    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiForCustomer::class.java)
        val retrofitData = retrofitBuilder.getLanguageData()

        retrofitData.enqueue(object : Callback<List<LawyerLanguageItems>?>{
            override fun onResponse(
                call: Call<List<LawyerLanguageItems>?>,
                response: Response<List<LawyerLanguageItems>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for (myData in responseBody) {
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append(myData.language)
                }
            }
            override fun onFailure(call: Call<List<LawyerLanguageItems>?>, t: Throwable) {

            }
        })
    }
}

