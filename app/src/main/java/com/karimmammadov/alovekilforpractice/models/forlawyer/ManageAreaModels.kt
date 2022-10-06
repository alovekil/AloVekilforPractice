package com.karimmammadov.alovekilforpractice.models.forlawyer


import android.content.Context
import android.util.Log

interface AreaInterface {
    fun getArea():List<LawyerAreaTypes>
    fun setArea(items: LawyerAreaTypes, context: Context):Boolean
    fun searchExistence(id:Int):Boolean
    fun removeItem(lawyerAreaTypes: LawyerAreaTypes)
    fun removeAllItems()
}

private class ManageAreaModels : AreaInterface {
        private val TAG = "myTag"
    private val areaSortedList = ArrayList<LawyerAreaTypes>()
    override fun getArea(): List<LawyerAreaTypes> {
       return areaSortedList
    }

    override fun setArea(items: LawyerAreaTypes, context:Context) : Boolean {
        Log.d(TAG, "setArea: ")
       if(areaSortedList.size == 5){
           Log.d(TAG, "setArea: if ")
           return false
       }else{
           Log.d(TAG, "setArea: else")
           areaSortedList.add(items)
       }
        return true
    }

    override fun searchExistence(id: Int): Boolean {
        return areaSortedList.map {
            it.id == id
        }.isNotEmpty()
    }

    override fun removeItem(lawyerAreaTypes: LawyerAreaTypes) {
        areaSortedList.remove(lawyerAreaTypes)
    }

    override fun removeAllItems() {
        areaSortedList.removeAll(areaSortedList.toSet())
    }
}
val GetManageInstanceAreas : AreaInterface = ManageAreaModels()