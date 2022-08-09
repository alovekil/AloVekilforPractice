package com.karimmammadov.alovekilforpractice

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class ViewPageAdapter (private var layout: List<View>) : RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>(){
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var itemLayout : View? = itemView.findViewById(R.id.lawRgstrPage1)
        var itemLayout2 : View? = itemView.findViewById(R.id.lawRgstrPage2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
       return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page,parent,false))
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.itemLayout = layout[position]
        holder.itemLayout2 = layout[position]
    }

    override fun getItemCount(): Int {
        return layout.size
    }

}