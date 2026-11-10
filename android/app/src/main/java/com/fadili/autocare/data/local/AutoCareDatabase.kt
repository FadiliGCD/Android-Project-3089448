package com.fadili.autocare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fadili.autocare.data.local.dao.UserProfileDao
import com.fadili.autocare.data.local.dao.VehicleDao
import com.fadili.autocare.data.local.entity.UserProfileEntity
import com.fadili.autocare.data.local.entity.VehicleEntity

@Database(
    entities = [
        UserProfileEntity::class,
        VehicleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AutoCareDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao

    abstract fun vehicleDao(): VehicleDao

    companion object {

        @Volatile
        private var INSTANCE: AutoCareDatabase? = null

        fun getDatabase(context: Context): AutoCareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AutoCareDatabase::class.java,
                    "autocare_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
