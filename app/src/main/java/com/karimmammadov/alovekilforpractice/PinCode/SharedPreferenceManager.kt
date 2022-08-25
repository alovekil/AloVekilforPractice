package com.karimmammadov.alovekilforpractice.PinCode

import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import java.util.*


class SharedPreferenceManager(activity: PendingIntent) {
    private val PRIVATE_MODE = 0
    var preferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var context: Context? = null

    fun SharedPreferences(context: Context){
        preferences = context.getSharedPreferences(context.packageName, PRIVATE_MODE)
        editor = preferences?.edit()
        this.context = context
    }
    fun isContained(s: String?): Boolean {
        return preferences!!.contains(s)
    }
    fun setValue(key: Any, value: Any) {
        if (value is String) {
            editor!!.putString(
                (key as String).lowercase(Locale.getDefault()),
                value.toString()
            ) // value to store
            editor!!.commit()
        } else if (value is Boolean) {
            editor!!.putBoolean(
                (key as String).lowercase(Locale.getDefault()),
                value
            ) // value to store
            editor!!.commit()
        } else if (value is Int) {
            editor!!.putInt(
                (key as String).lowercase(Locale.getDefault()),
                value
            ) // value to store
            editor!!.commit()
        } else if (value is Long) {
            editor!!.putLong(
                (key as String).lowercase(Locale.getDefault()),
                value
            ) // value to store
            editor!!.commit()
        }
    }

    fun getString(key: String, defValue: String?): String? {
        return preferences!!.getString(key.lowercase(Locale.getDefault()), defValue)
    }

    fun getBoolean(key: String, defValue: Boolean?): Boolean? {
        return preferences!!.getBoolean(key.lowercase(Locale.getDefault()), defValue!!)
    }

    fun getInt(key: String, defValue: Int?): Int? {
        return preferences!!.getInt(key.lowercase(Locale.getDefault()), defValue!!)
    }

    fun getLong(key: String, defValue: Long?): Long? {
        return preferences!!.getLong(key.lowercase(Locale.getDefault()), defValue!!)
    }
}