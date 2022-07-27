package com.karimmammadov.alovekilforpractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu

import com.karimmammadov.alovekilforpractice.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_o_t_p_code.view.*
import kotlinx.android.synthetic.main.fragment_phone_number.*


class PhoneNumberFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // dropDownNumberMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_enterNumber.setOnClickListener {
            val fragmentManager = getFragmentManager()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            val fragmentNumber = OTPCodeFragment()
            fragmentTransaction?.replace(R.id.frameLayout, fragmentNumber)?.commit()
        }
    }
}
/*
    private fun dropDownNumberMenu(){
        val popupMenu = PopupMenu(this.activity,btn_numberFilter)
        popupMenu.inflate(R.menu.popup_numberfiltermenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.number1 ->{
                    btn_numberFilter.setText("050")
                    true
                }
                R.id.number2 ->{
                    btn_numberFilter.setText("051")
                    true
                }
                R.id.number3 ->{
                    btn_numberFilter.setText("055")
                    true
                }
                R.id.number4 ->{
                    btn_numberFilter.setText("070")
                    true
                }
                R.id.number5 ->{
                    btn_numberFilter.setText("077")
                    true
                }
                else -> true
            }
        }
    }
 */