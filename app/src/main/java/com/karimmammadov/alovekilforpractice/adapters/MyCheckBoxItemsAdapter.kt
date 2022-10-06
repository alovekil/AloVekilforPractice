package com.karimmammadov.alovekilforpractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.alovekilforpractice.R
import com.karimmammadov.alovekilforpractice.models.forlawyer.GetManageInstance
import com.karimmammadov.alovekilforpractice.models.forlawyer.GetManageInstanceAreas
import com.karimmammadov.alovekilforpractice.models.forlawyer.LawyerLanguageItems
import kotlinx.android.synthetic.main.checkbox_items.view.*

class MyCheckBoxItemsAdapter(val context: Context, val languageList: List<LawyerLanguageItems>):
    RecyclerView.Adapter<MyCheckBoxItemsAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var languageType: TextView
        val checkBox: CheckBox
        init {
            languageType = itemView.language_sort
            checkBox = itemView.checkbox
        }

        fun onClickListener(position: Int){
            checkBox.setOnClickListener {
                if(checkBox.isChecked){
                    GetManageInstance.setLanguage(languageList.get(position))

                }else{
                    GetManageInstance.removeItem(languageList.get(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.checkbox_items,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.isChecked = false
        holder.languageType.text = languageList[position].language.toString()
        holder.onClickListener(position)

        holder.checkBox.isChecked = isCheckedFromHistory(languageList[position].language)
    }

    override fun getItemCount(): Int {
        return languageList.size
    }
    private fun isCheckedFromHistory(item:String):Boolean {
        val singleAreaList = GetManageInstance.getLanguage().map {
            it.language
        }
        return singleAreaList.contains(item)
    }
}