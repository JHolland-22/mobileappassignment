package ie.setu.assignment1.main

import android.app.Application
import android.widget.AdapterView
import ie.setu.assignment1.models.ClothMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {



    val cloths = ClothMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Clothes started")


    }
}