package ge.george.androidmidterm2

import android.app.Application
import ge.george.androidmidterm2.db.room.MyDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MyDatabase.getInstance(this)
    }
}