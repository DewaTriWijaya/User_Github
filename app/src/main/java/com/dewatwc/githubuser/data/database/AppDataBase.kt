package com.dewatwc.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dewatwc.githubuser.data.DatUser

@Database(entities = [DatUser::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_data_base"
                ).build()
            }
    }
}
