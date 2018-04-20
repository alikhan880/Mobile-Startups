package kz.kbtu.pixabaygold.helpers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.kbtu.pixabaysilver.helpers.PixabayImages
import kz.kbtu.pixabaysilver.helpers.PixabayVideos

public class Utils{
    companion object {
        var prefs : SharedPreferences? = null
        fun init(c : Context){
            prefs = c.getSharedPreferences("favs", Context.MODE_PRIVATE)
        }

        fun saveFavsImages(list : ArrayList<PixabayImages>){
            val gson = Gson()
            val s = gson.toJson(list)
            prefs!!.edit().putString("images", s).apply()
        }

        fun getFavsImages() : ArrayList<PixabayImages> {
            val gson = Gson()
            val s = prefs!!.getString("images", null)
            if(s != null){
                return gson.fromJson(s, object : TypeToken<List<PixabayImages>>(){}.type)
            }
            return arrayListOf()
        }
        fun saveFavsVideos(list : ArrayList<PixabayVideos>){
            val gson = Gson()
            val s = gson.toJson(list)
            prefs!!.edit().putString("videos", s).apply()
        }

        fun getFavsVideos() : ArrayList<PixabayVideos> {
            val gson = Gson()
            val s = prefs!!.getString("videos", null)
            if(s != null){
                return gson.fromJson(s, object : TypeToken<List<PixabayVideos>>(){}.type)
            }
            return arrayListOf()
        }
    }
}