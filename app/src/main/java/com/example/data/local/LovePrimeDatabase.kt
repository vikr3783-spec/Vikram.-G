package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.LovePrimeDao
import com.example.data.local.entity.CheckInEntity
import com.example.data.local.entity.DateNightEntity
import com.example.data.local.entity.LoveCouponEntity
import com.example.data.local.entity.MemoryEntity

@Database(
    entities = [
        LoveCouponEntity::class,
        MemoryEntity::class,
        DateNightEntity::class,
        CheckInEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LovePrimeDatabase : RoomDatabase() {

    abstract fun lovePrimeDao(): LovePrimeDao

    companion object {
        @Volatile
        private var INSTANCE: LovePrimeDatabase? = null

        fun getDatabase(context: Context): LovePrimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LovePrimeDatabase::class.java,
                    "love_prime_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
