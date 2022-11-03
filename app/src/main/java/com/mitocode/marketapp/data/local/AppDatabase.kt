package com.mitocode.marketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCategory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    // static
    /* companion object {
        private var instanceDB: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instanceDB == null){
                instanceDB = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marketDB"
                ).build()
            }
            return instanceDB!!
        }
    }*/
}