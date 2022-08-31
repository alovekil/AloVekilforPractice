package com.karimmammadov.alovekilforpractice.models

interface AreaInterface {
    fun getArea():List<LawyerAreaTypes>
    fun setArea(items: LawyerAreaTypes)
    fun searchExistence(id:Int):Boolean
    fun removeItem(lawyerAreaTypes: LawyerAreaTypes)
    fun removeAllItems()
}

private class ManageAreaModels : AreaInterface {

    private var areaSortedList = ArrayList<LawyerAreaTypes>()
    override fun getArea(): List<LawyerAreaTypes> {
       return areaSortedList
    }

    override fun setArea(items: LawyerAreaTypes) {
       areaSortedList.add(items)
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
        areaSortedList = ArrayList<LawyerAreaTypes>()
    }
}
val GetManageInstanceAreas : AreaInterface = ManageAreaModels()