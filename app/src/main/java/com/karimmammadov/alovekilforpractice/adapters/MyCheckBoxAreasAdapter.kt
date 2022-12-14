package com.karimmammadov.alovekilforpractice.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.models.forlawyer.GetManageInstanceAreas
import com.karimmammadov.alovekilforpractice.models.forlawyer.LawyerAreaTypes
import kotlinx.android.synthetic.main.checkboxareas_items.view.*
import kotlin.math.log


class MyCheckBoxAreasAdapter (val context: Context, val areasList: List<LawyerAreaTypes>):
    RecyclerView.Adapter<MyCheckBoxAreasAdapter.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var areaType: TextView
        val checkBox: CheckBox

        init {

            areaType = itemView.areas_sort
            checkBox = itemView.checkboxAreas
        }
        fun onClickListener(position: Int){
            checkBox.setOnClickListener {
                if(checkBox.isChecked){
                    GetManageInstanceAreas.setArea(areasList.get(position),context)
                        .also { checkBox.isChecked = it
                        if (!it){
                            Toast.makeText(context,"You must only select 5 areas", Toast.LENGTH_SHORT).show()
                        }}
                }else{
                    GetManageInstanceAreas.removeItem(areasList.get(position))
                }
            }
        }




    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.checkboxareas_items,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.isChecked = false
        holder.areaType.text = areasList[position].service_name
        holder.onClickListener(position)
        holder.checkBox.isChecked = isCheckedFromHistory(areasList[position].service_name)

    }

    override fun getItemCount(): Int {
       return areasList.size
    }

    private fun isCheckedFromHistory(item:String):Boolean{
        val singleAreaList = GetManageInstanceAreas.getArea().map{
            it.service_name
        }

        Log.d("MyTagHere", "isCheckedFromHistory: $singleAreaList")

        return singleAreaList.contains(item)
    }
}