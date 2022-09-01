package com.karimmammadov.alovekilforpractice.fragments

import android.app.Activity.RESULT_OK
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
import androidx.fragment.app.Fragment
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxAreasAdapter
import com.karimmammadov.alovekilforpractice.adapters.MyCheckBoxItemsAdapter
import com.karimmammadov.alovekilforpractice.api.ApiForCustomer
import com.karimmammadov.alovekilforpractice.api.ApiForLawyer
import com.karimmammadov.alovekilforpractice.api.RetrofitClientForLawyer
import com.karimmammadov.alovekilforpractice.models.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.*
import kotlinx.android.synthetic.main.fragment_lawyer_register_page2.view.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.spec.PSSParameterSpec.DEFAULT
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class LawyerRegisterPage2 : Fragment() {
    val BASE_URL = "http://38.242.221.247/api/"
    lateinit var myCheckBoxItemsAdapter: MyCheckBoxItemsAdapter
    lateinit var myCheckBoxAreasAdapter: MyCheckBoxAreasAdapter
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var lawyer : Lawyer
    val lawyerLanguages = ArrayList<Int>()
    val lawyerAreas = ArrayList<Int>()
    lateinit var lawyerModels: LawyerModels

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
                for(l in 0..selectedLanguage.size-1){
                    lawyerLanguages.add(selectedLanguage.get(l+1).id)
                    stringBuilder.append(", ${selectedLanguage.get(l+1).language}")
                }
                languageTextView.setText(stringBuilder)
            }
        }

        val areasTextView = view.findViewById<TextView>(R.id.tv_areas)
        areasTextView.setOnClickListener {
            GetManageInstanceAreas.removeAllItems()
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            )
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.layout_spinnerareas, null, false)
            builder.setView(view)
            val builderCreate = builder.create()
            builderCreate.show()
            val recyclerViewAreas = view.findViewById<RecyclerView>(R.id.area_rcyvw)
            var areaList = listOf(
                LawyerAreaTypes(4,"Müqavilə Hüququ"),
                LawyerAreaTypes(5, "Sahibkarlıq hüququ"),
                LawyerAreaTypes(6, "Mənzil və daşınmaz əmlak hüququ"),
                LawyerAreaTypes(7, "Əmək və sosial təminat (pensiya) hüququ"),
                LawyerAreaTypes(8, "Ailə hüququ"),
                LawyerAreaTypes(9, "Cinayət hüququ"),
                LawyerAreaTypes(10, "Gömrük hüququ"),
                LawyerAreaTypes(11, "Vergi hüququ"),
                LawyerAreaTypes(12, "Maliyyə (bank, sığorta və s.) hüququ"),
                LawyerAreaTypes(13, "Əqli mülkiyyət hüququ"),
                LawyerAreaTypes(14, "Miqrasiya hüququ"),
                LawyerAreaTypes(15, "İstehlakçı hüququ"),
                LawyerAreaTypes(16, "Mülki hüquq"),
                LawyerAreaTypes(17, "İnzibati hüquq (Dövlət qurumları ilə iş)"),
                LawyerAreaTypes(18, "Yol-hərəkəti"),
                LawyerAreaTypes(19, "Məhkəmə qərarlarının icrası"),
                LawyerAreaTypes(20, "Mülki-prosessual hüquq"),
                LawyerAreaTypes(21, "Satınalma (tender) hüququ")
            )
            myCheckBoxAreasAdapter = MyCheckBoxAreasAdapter(requireContext(), areaList)
            recyclerViewAreas.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAreas.adapter = myCheckBoxAreasAdapter
            view.findViewById<TextView>(R.id.tv_clearAllAreas).setOnClickListener {
                myCheckBoxAreasAdapter.notifyItemRangeChanged(0, areaList.size )
                GetManageInstanceAreas.removeAllItems()
            }
            view.findViewById<TextView>(R.id.tv_cancelAreas).setOnClickListener {
                builderCreate.dismiss()
                GetManageInstanceAreas.removeAllItems()
            }

            view.findViewById<TextView>(R.id.tv_OkAreas).setOnClickListener {
                val stringBuilder = StringBuilder()
                builderCreate.dismiss()
                val selectedArea = GetManageInstanceAreas.getArea()
                stringBuilder.append(selectedArea.get(0).service_name)
                for(l in 0..selectedArea.size-1){
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

        view.saveButton.setOnClickListener {
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

            val lglexperience = view.editLegalExperience.text.toString().trim()
            val lawyerExperience = view.editLawyerExperience.text.toString().trim()
            val lawyerTaxVoen = view.lawyerVoen.text.toString().trim()
            val lawyerfirstPassword = view.editPasswordLawyer.text.toString().trim()
            val lawyerconfrimPassword = view.editConfirmPasswordLawyer.text.toString().trim()
            /*
            val serviceTypesLawyers = ArrayList<Int>()
            serviceTypesLawyers.add(4)
            serviceTypesLawyers.add(7)

            val serviceLanguagesLawyer = ArrayList<Int>()
            serviceLanguagesLawyer.add(1)
             */


            val birth_date = sharedPreferences.getString("lawyerDateBirth",null)!!
            val certificate = listCertificate
            val father_name = sharedPreferences.getString("userLawyerFatherName",null)!!
            val gender = sharedPreferences.getString("userLawyerGender",null)!!
            val law_practice = lglexperience
            val lawyer_practice = lawyerExperience
            val lawyer_card = diploma
            val service_languages = lawyerLanguages
            val service_types = lawyerAreas
            val university = sharedPreferences.getString("lawyerUniversity",null)!!
            val voen = lawyerTaxVoen
            lawyer = Lawyer(birth_date, certificate, father_name, gender, law_practice, lawyer_card,
                              lawyer_practice, service_languages, service_types, university, voen)

            val  lawyerModels_lawyer = lawyer

            val email = sharedPreferences.getString("lawyeremail",null)!!
            val first_name = sharedPreferences.getString("userLawyerName",null)!!
            val last_name = sharedPreferences.getString("userLawyerSurname",null)!!
            val phone ="+994554046560"
            val password = lawyerfirstPassword
            val password2 = lawyerconfrimPassword

            lawyerModels = LawyerModels(email, first_name, last_name, lawyerModels_lawyer, password, password2, phone)

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
                    if (t is HttpException){
                        println("HttpException")
                        println("Code: "+t.code())
                        println("Response: "+t.response())
                    }
                }

            })

        }

        getMyData()

        return view
    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiForLawyer::class.java)
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
            if (result.resultCode == RESULT_OK) {
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
                            view!!.certificateImage.setImageBitmap(selectedBitmapCertificate)
                        } else {
                            selectedBitmapCertificate = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                selectedPictureCertificate
                            )
                            view!!.certificateImage.setImageBitmap(selectedBitmapCertificate)
                        }
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
            if (result.resultCode == RESULT_OK) {
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
                            view!!.diplomaImage.setImageBitmap(selectedBitmapDiploma)
                        } else {
                            selectedBitmapDiploma = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                selectedPictureDiploma
                            )
                            view!!.diplomaImage.setImageBitmap(selectedBitmapDiploma)
                        }
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

