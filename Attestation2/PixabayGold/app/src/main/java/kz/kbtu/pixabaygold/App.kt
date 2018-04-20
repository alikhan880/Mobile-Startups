package kz.kbtu.pixabaygold

import android.app.Application
import kz.kbtu.pixabaygold.helpers.Utils

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}