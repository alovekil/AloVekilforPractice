package com.karimmammadov.alovekilforpractice.models

interface LanguageInterface {
    fun getLanguage():List<LawyerLanguageItems>
    fun setLanguage(items: LawyerLanguageItems)
    fun searchExistence(id:Int):Boolean
    fun removeItem(lawyerLanguageItems: LawyerLanguageItems)
    fun removeAllItems()
}

private class ManageLanguageModels : LanguageInterface{

    private var languageSortedList = ArrayList<LawyerLanguageItems>()

    override fun getLanguage(): List<LawyerLanguageItems> {
        return languageSortedList
    }

    override fun setLanguage(items: LawyerLanguageItems) {
        languageSortedList.add(items)
    }

    override fun searchExistence(id: Int): Boolean {
        return languageSortedList.map {
            it.id == id
        }.isNotEmpty()
    }

    override fun removeItem(lawyerLanguageItems: LawyerLanguageItems) {
        languageSortedList.remove(lawyerLanguageItems)
    }

    override fun removeAllItems() {
        languageSortedList = ArrayList<LawyerLanguageItems>()
    }

}
val GetManageInstance : LanguageInterface = ManageLanguageModels()