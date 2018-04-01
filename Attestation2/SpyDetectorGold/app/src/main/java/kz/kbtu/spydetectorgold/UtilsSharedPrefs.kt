package kz.kbtu.spydetectorgold

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by abakh on 15-Mar-18.
 */
class UtilsSharedPrefs {
    companion object {
        var sp : SharedPreferences? = null
        var context : Context? = null


        fun init(context : Context){
            this.context = context
            sp = context.getSharedPreferences("table", Context.MODE_PRIVATE)
        }

        fun saveResult(r : ArrayList<Result>){
            val gson = Gson()
            val s = gson.toJson(r)
            sp!!.edit().putString("results", s).apply()
        }


        fun getResults():ArrayList<Result>{
            val gson = Gson()
            val s = sp!!.getString("results", null)
            if(s != null){
                return gson.fromJson(s, object : TypeToken<List<Result>>(){}.type)
            }
            return arrayListOf()
        }
    }




}