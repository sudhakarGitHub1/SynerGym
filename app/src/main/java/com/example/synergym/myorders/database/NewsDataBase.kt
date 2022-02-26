package com.example.synergym.myorders.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.synergym.myorders.data.NewsData


@Database(entities = [NewsData::class], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun userDao(): DaoAccess?

    companion object {
        private var appDatabase: NewsDataBase? = null

        /**
         * from developers android, made my own singleton
         *
         * @param context
         * @return
         */
        fun getInstance(context: Context): NewsDataBase? {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java, "database-name"
                ).build()
            }
            return appDatabase
        }
    }
}