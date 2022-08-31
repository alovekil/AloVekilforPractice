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
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.view.*
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
        val lglexperience = view.editLegalExperience.text.toString().trim()
        //val diploma = editDiplomaLawyer.text.toString().trim()
        val lawyerExperience = view.editLawyerExperience.text.toString().trim()
       val serviceTypesLawyers = ArrayList<Int>()
        serviceTypesLawyers.add(4)
        serviceTypesLawyers.add(7)
        val lawyerTaxVoen = view.lawyerVoen.text.toString().trim()
       val serviceLanguagesLawyer = ArrayList<Int>()
        serviceLanguagesLawyer.add(1)

        lawyer = Lawyer("19.05.2002",listCertificate,"Babek","Male","2","3",
            "Lawyer Card",serviceLanguagesLawyer,serviceTypesLawyers,"BMU","AZ1069")
        /*
        lawyer.birth_date = "19.05.2002"
        lawyer.certificate = listCertificate
        lawyer.father_name = "Babek"
        lawyer.gender = "Male"
        lawyer.law_practice = "2"
        lawyer.lawyer_practice = "3"
        lawyer.lawyer_card = "Lawyer Card"
        lawyer.service_languages = serviceLanguagesLawyer
        lawyer.service_types = serviceTypesLawyers
        lawyer.university = "BMU"
        lawyer.voen = "AZ1069"


         */
        lawyerModels = LawyerModels("uu811717@gmail.com","Kerim","Mammadov",
            lawyer,"lawyer123","lawyer123","+994554046560")

       // lawyerModels.lawyer = lawyer

        /*
        lawyerModels.email = "uu811717@gmail.com"
        lawyerModels.first_name = "Kerim"
        lawyerModels.last_name = "Mammadov"
        lawyerModels.phone = "+994554046560"
        lawyerModels.password = "lawyer123"
        lawyerModels.password2 = "lawyer123"


         */
        try {
            RetrofitClientForLawyer.instance.createUserLawyer(lawyerModels).enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                   println(response.message() + "Success")
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    println(t)
                    println("Error")
                }

            })
        }catch (e : Exception){
            e.localizedMessage
        }


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

