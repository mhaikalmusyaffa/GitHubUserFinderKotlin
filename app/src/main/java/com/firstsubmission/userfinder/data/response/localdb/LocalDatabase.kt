package com.firstsubmission.userfinder.data.response.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firstsubmission.userfinder.data.model.Entity

@Database(
    entities = [Entity::class],
    version = 1
)
abstract class LocalDatabase: RoomDatabase(){
    companion object {
        private var INSTANCE : LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase?{
            if(INSTANCE == null){
                synchronized(LocalDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, "mk_codehub_db").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUserDao(): DataAccessObject
}