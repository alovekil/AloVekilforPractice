package com.karimmammadov.alovekilforpractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.models.GetManageInstance
import com.karimmammadov.alovekilforpractice.models.GetManageInstanceAreas
import com.karimmammadov.alovekilforpractice.models.LawyerAreaTypes
import kotlinx.android.synthetic.main.checkbox_items.view.*
import kotlinx.android.synthetic.main.checkboxareas_items.view.*


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
                    GetManageInstanceAreas.setArea(areasList.get(position))
                }else{
                    GetManageInstanceAreas.removeItem(areasList.get(position))
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.checkboxareas_items,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.isChecked = false
        holder.areaType.text = areasList[position].service_name
        holder.onClickListener(position)
    }

    override fun getItemCount(): Int {
       return areasList.size
    }
}