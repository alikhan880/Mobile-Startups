package kz.kbtu.spydetectorgold

import android.app.Application

/**
 * Created by abakh on 15-Mar-18.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UtilsSharedPrefs.init(this)
    }




}