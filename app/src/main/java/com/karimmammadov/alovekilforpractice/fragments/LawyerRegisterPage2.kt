package com.karimmammadov.alovekilforpractice.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxItemsAdapter
import com.karimmammadov.alovekilforpractice.api.ApiForCustomer
import com.karimmammadov.alovekilforpractice.models.GetManageInstance
import com.karimmammadov.alovekilforpractice.models.LawyerLanguageItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LawyerRegisterPage2 : Fragment() {
    val BASE_URL = "http://38.242.221.247/api/"
    lateinit var myCheckBoxItemsAdapter: MyCheckBoxItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_lawyer_register_page2, container, false)

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
                    stringBuilder.append(", ${selectedLanguage.get(l).language}")
                }
                languageTextView.setText(stringBuilder)
            }
        }
        getMyData()

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

