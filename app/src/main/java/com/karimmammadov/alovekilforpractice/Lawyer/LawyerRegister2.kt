package com.karimmammadov.alovekilforpractice.Lawyer

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxAreasAdapter
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxItemsAdapter
import com.karimmammadov.alovekilforpractice.api.ApiForLawyer
import com.karimmammadov.alovekilforpractice.api.RetrofitClientForLawyer
import com.karimmammadov.alovekilforpractice.models.*
import kotlinx.android.synthetic.main.fragment_customer_register.view.*
import kotlinx.android.synthetic.main.fragment_lawyer_register2.*
import kotlinx.android.synthetic.main.fragment_lawyer_register2.view.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException


class LawyerRegister2 : Fragment() {
    val BASE_URL = "http://38.242.221.247/api/"
    lateinit var myCheckBoxItemsAdapter: MyCheckBoxItemsAdapter
    lateinit var myCheckBoxAreasAdapter: MyCheckBoxAreasAdapter
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var lawyer : Lawyer
    val lawyerLanguages = ArrayList<Int>()
    val lawyerAreas = ArrayList<Int>()
    lateinit var lawyerModels: LawyerModels
    private lateinit var profileSharedPreferences: SharedPreferences


    //Image
    var selectedPictureCertificate : Uri? = null
    var selectedBitmapCertificate : Bitmap? = null
    private lateinit var activityResultLauncherCertificate: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncherCertificate: ActivityResultLauncher<String>

    //Diploma
    var selectedPictureDiploma : Uri? = null
    var selectedBitmapDiploma : Bitmap? = null
    private lateinit var activityResultLauncherDiploma: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncherDiploma: ActivityResultLauncher<String>

    val lawyerlanguageitems = ArrayList<LawyerLanguageItems>()
    val lawyerareastype = ArrayList<LawyerAreaTypes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(com.karimmammadov.alovekilforpractice.R.layout.fragment_lawyer_register2, container, false)

        sharedPreferences = requireContext().getSharedPreferences("lawyer", Context.MODE_PRIVATE)
        getMyData()
        getMyAreasData()
        val languageTextView = view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_languages)
        languageTextView.setOnClickListener {
            GetManageInstance.removeAllItems()
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            val view = LayoutInflater.from(requireContext()).inflate(com.karimmammadov.alovekilforpractice.R.layout.layout_spinner, null, false)
            builder.setView(view)
            val builderCreate = builder.create()
            builderCreate.show()
            val recyclerView = view.findViewById<RecyclerView>(com.karimmammadov.alovekilforpractice.R.id.language_rcyvw)

            myCheckBoxItemsAdapter = MyCheckBoxItemsAdapter(requireContext(), lawyerlanguageitems)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = myCheckBoxItemsAdapter
            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_clearAll).setOnClickListener {
                myCheckBoxItemsAdapter.notifyItemRangeChanged(0, lawyerlanguageitems.size )
                GetManageInstance.removeAllItems()
            }
            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_cancel).setOnClickListener {
                builderCreate.dismiss()
                GetManageInstance.removeAllItems()
            }

            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_Ok).setOnClickListener {
                val stringBuilder = StringBuilder()
                builderCreate.dismiss()
                val selectedLanguage = GetManageInstance.getLanguage()
                if (selectedLanguage.isNotEmpty()){
                    stringBuilder.append(selectedLanguage.get(0).language)
                }
                lawyerLanguages.clear()
                for(l in 1..selectedLanguage.size-1){
                    lawyerLanguages.add(selectedLanguage.get(l).id)
                    stringBuilder.append(", ${selectedLanguage.get(l).language}")
                }
                languageTextView.setText(stringBuilder)
            }
        }

        val areasTextView = view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_areas)
        areasTextView.setOnClickListener {
            GetManageInstanceAreas.removeAllItems()
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            val view = LayoutInflater.from(requireContext()).inflate(com.karimmammadov.alovekilforpractice.R.layout.layout_spinnerareas, null, false)
            builder.setView(view)
            val builderCreate = builder.create()
            builderCreate.show()
            val recyclerViewAreas = view.findViewById<RecyclerView>(com.karimmammadov.alovekilforpractice.R.id.area_rcyvw)

            myCheckBoxAreasAdapter = MyCheckBoxAreasAdapter(requireContext(), lawyerareastype)
            recyclerViewAreas.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAreas.adapter = myCheckBoxAreasAdapter
            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_clearAllAreas).setOnClickListener {
                myCheckBoxAreasAdapter.notifyItemRangeChanged(0, lawyerareastype.size )
                GetManageInstanceAreas.removeAllItems()
            }
            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_cancelAreas).setOnClickListener {
                builderCreate.dismiss()
                GetManageInstanceAreas.removeAllItems()
            }

            view.findViewById<TextView>(com.karimmammadov.alovekilforpractice.R.id.tv_OkAreas).setOnClickListener {
                val stringBuilder = StringBuilder()
                builderCreate.dismiss()
                val selectedArea = GetManageInstanceAreas.getArea()
                if(selectedArea.isNotEmpty()){
                    stringBuilder.append(selectedArea.get(0).service_name)
                }
                lawyerAreas.clear()
                for(l in 1..selectedArea.size-1){
                    lawyerAreas.add(selectedArea.get(l).id)
                    stringBuilder.append(", ${selectedArea.get(l).service_name}")
                }
                areasTextView.setText(stringBuilder)
            }
        }

        registerCertificate()
        view.lawyerCertificate.setOnClickListener {
            selectCertificate(it)
        }

        registerDiploma()
        view.editDiplomaLawyer.setOnClickListener {
            selectedDiploma(it)
        }
        view.moveBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_lawyerRegister2_to_lawyerRegister12)
        }

        view.saveButton.setOnClickListener {


            val lglexperience = view.editLegalExperience.text.toString().trim()
            val lawyerExperience = view.editLawyerExperience.text.toString().trim()
            val lawyerTaxVoen = view.lawyerVoen.text.toString().trim()
            val lawyerfirstPassword = view.editPasswordLawyer.text.toString().trim()
            val lawyerconfrimPassword = view.editConfirmPasswordLawyer.text.toString().trim()


            if (lglexperience.isEmpty()){
                editLegalExperience.error = "Legal Experience required"
                editLegalExperience.requestFocus()
            }
            if (lawyerExperience.isEmpty()){
                editLawyerExperience.error = "Lawyer Experience required"
                editLawyerExperience.requestFocus()
            }
            if (lawyerTaxVoen.isEmpty()){
                lawyerVoen.error = "Voen required"
                lawyerVoen.requestFocus()
            }
            if (lawyerfirstPassword.isEmpty()){
                editPasswordLawyer.error = "Password required"
                editPasswordLawyer.requestFocus()
            }
            if (lawyerconfrimPassword.isEmpty()){
                editConfirmPasswordLawyer.error = "Confirm Password required"
                editConfirmPasswordLawyer.requestFocus()
            }
            if (lawyerconfrimPassword.isEmpty()){
                editConfirmPasswordLawyer.error = "Confirm Password required"
                editConfirmPasswordLawyer.requestFocus()
            }


            view.certificateImage.invalidate()
            var bitmapC = view.certificateImage.getDrawable().toBitmap()
            val streamC = ByteArrayOutputStream()
            bitmapC.compress(Bitmap.CompressFormat.JPEG, 100, streamC)
            val byteArrayC = streamC.toByteArray()
            val encodedStringC = Base64.encodeToString(byteArrayC, Base64.DEFAULT)
            println(encodedStringC)

            view.diplomaImage.invalidate()
            var bitmapD = view.diplomaImage.getDrawable().toBitmap()
            val streamD = ByteArrayOutputStream()
            bitmapD.compress(Bitmap.CompressFormat.JPEG, 100, streamD)
            val byteArrayD = streamD.toByteArray()
            val encodedStringD = Base64.encodeToString(byteArrayD, Base64.DEFAULT)
            println(encodedStringD)

            val listCertificate = ArrayList<String>()
            listCertificate.add(encodedStringC)

            var diploma = encodedStringD


            val birth_date = sharedPreferences.getString("lawyerDateBirth","null")!!
            val certificate = listCertificate
            val father_name = sharedPreferences.getString("userLawyerFatherName","null")!!
            val gender = sharedPreferences.getString("userLawyerGender","null")!!
            val law_practice = lglexperience
            val lawyer_practice = lawyerExperience
            val lawyer_card = diploma
            val service_languages = lawyerLanguages
            val service_types = lawyerAreas
            val university = sharedPreferences.getString("lawyerUniversity","null")!!
            val voen = lawyerTaxVoen

            lawyer = Lawyer(birth_date, certificate, father_name, gender, law_practice, lawyer_card,
                lawyer_practice, service_languages, service_types, university, voen)

            val  lawyerModels_lawyer = lawyer

            val emailLawyer = sharedPreferences.getString("lawyeremail","null")!!
            val first_name = sharedPreferences.getString("userLawyerName","null")!!
            val last_name = sharedPreferences.getString("userLawyerSurname","null")!!
            val phone =sharedPreferences.getString("lawyerPhoneNumber","null")!!
            val password = lawyerfirstPassword
            val password2 = lawyerconfrimPassword

            lawyerModels = LawyerModels(emailLawyer,first_name,last_name,lawyerModels_lawyer,password,password2,phone)

            RetrofitClientForLawyer.instance.createUserLawyer(lawyerModels).enqueue(object :
                Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    println(response.message() + "Success")
                    if(response.body()?.response.equals("successfully regitered a new user.")){
                        val myresponse : String = response.body()?.response.toString()
                        Toast.makeText(requireContext(),"successfully regitered a new user.", Toast.LENGTH_SHORT).show()
                       // val intent = Intent(context!!.applicationContext, CreatePasswordActivity::class.java)
                       // startActivity(intent)
                        findNavController().navigate(R.id.action_lawyerRegister2_to_alertDialogLawyer)

                    }

                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    val myString : String = t.message.toString()
                    Toast.makeText(requireContext(),myString, Toast.LENGTH_LONG).show()
                }


            })


            /*
            val apiForLawyer : ApiForLawyer = RetrofitClientForLawyer.buildService(ApiForLawyer::class.java)
            val requestCall : Call<DefaultResponse> = apiForLawyer.createUserLawyer(lawyerModels)

            requestCall.enqueue(object: Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(requireContext(),"Succesfully added",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),"Failed to add item",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Failed to add item",Toast.LENGTH_SHORT).show()
                }

            })

             */
        }
/*
        println(response.message() + "Success")
        Toast.makeText(requireContext(),"Servere melumat gonderildi",Toast.LENGTH_SHORT).show()
        val intent = Intent(context!!.applicationContext,CreatePasswordActivity::class.java)
        startActivity(intent)

          Toast.makeText(requireContext(),"Servere melumat gonderilmedi! Melumatlarin duzgunluyunu yoxlayin",Toast.LENGTH_SHORT).show()
                    println(t)
                    println("Error")
                    if (t is HttpException){
                        println("HttpException")
                        println("Code: "+t.code())
                        println("Response: "+t.response())
                    }
*/



        return view
    }



    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiForLawyer::class.java)
        val retrofitData = retrofitBuilder.getLanguageData()

        retrofitData.enqueue(object : Callback<List<LawyerLanguageItems>?> {
            override fun onResponse(
                call: Call<List<LawyerLanguageItems>?>,
                response: Response<List<LawyerLanguageItems>?>
            ) {
                val responseBody = response.body()!!

                for (myData in responseBody) {
                    val lawyerLanguageItems = LawyerLanguageItems(myData.id,myData.language)
                   lawyerlanguageitems.add(lawyerLanguageItems)
                }
            }
            override fun onFailure(call: Call<List<LawyerLanguageItems>?>, t: Throwable) {

            }
        })
    }

    private fun getMyAreasData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiForLawyer::class.java)
        val retrofitData = retrofitBuilder.getAreasData()

        retrofitData.enqueue(object : Callback<List<LawyerAreaTypes>?> {
            override fun onResponse(
                call: Call<List<LawyerAreaTypes>?>,
                response: Response<List<LawyerAreaTypes>?>
            ) {
                val responseBody = response.body()!!

                for (myData in responseBody){
                    val lawyerAreaTypes = LawyerAreaTypes(myData.id,myData.service_name)
                    lawyerareastype.add(lawyerAreaTypes)
                }
            }

            override fun onFailure(call: Call<List<LawyerAreaTypes>?>, t: Throwable) {

            }

        })
    }

    private fun selectCertificate(view:View){
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        permissionLauncherCertificate.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
            } else {
                permissionLauncherCertificate.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncherCertificate.launch(intentToGallery)
        }
    }

    private fun registerCertificate(){
        activityResultLauncherCertificate = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPictureCertificate = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                requireContext().contentResolver,
                                selectedPictureCertificate!!
                            )
                            selectedBitmapCertificate = ImageDecoder.decodeBitmap(source)
                            requireView().certificateImage.setImageBitmap(selectedBitmapCertificate)
                        } else {
                            selectedBitmapCertificate = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                selectedPictureCertificate
                            )
                            requireView().certificateImage.setImageBitmap(selectedBitmapCertificate)
                        }
                        lawyerCertificate.setText(selectedPictureCertificate?.path)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncherCertificate = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncherCertificate.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectedDiploma(view:View){
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        permissionLauncherDiploma.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
            } else {
                permissionLauncherDiploma.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncherDiploma.launch(intentToGallery)
        }
    }

    private fun registerDiploma(){
        activityResultLauncherDiploma = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPictureDiploma = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                requireContext().contentResolver,
                                selectedPictureDiploma!!
                            )
                            selectedBitmapDiploma = ImageDecoder.decodeBitmap(source)
                            requireView().diplomaImage.setImageBitmap(selectedBitmapDiploma)
                        } else {
                            selectedBitmapDiploma = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                selectedPictureDiploma
                            )
                            requireView().diplomaImage.setImageBitmap(selectedBitmapDiploma)
                        }
                        editDiplomaLawyer.setText(selectedPictureDiploma?.path)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncherDiploma= registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncherDiploma.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
    }


}