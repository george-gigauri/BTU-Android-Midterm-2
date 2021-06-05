package ge.george.androidmidterm2.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.george.androidmidterm2.db.dao.ProductDao
import ge.george.androidmidterm2.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = true)
abstract class MyDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance() = instance

        fun getInstance(context: Context) =
            instance ?: Room.databaseBuilder(context, MyDatabase::class.java, "my_db")
                .allowMainThreadQueries()
                .build().also {
                    instance = it
                }
    }
}