package com.example.gymmate.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymmate.data.dailytrack.DailyTrack
import com.example.gymmate.data.dailytrack.DailyTrackDao
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.data.exercisedata.ExerciseDao
import com.example.gymmate.data.userdata.UserEntity
import com.example.gymmate.data.userdata.UserEntityDao

@Database(
    entities = [Exercise::class, UserEntity::class, DailyTrack::class],
    version = 1,
    exportSchema = false
)
abstract class GymmateEmbeddedDatabase : RoomDatabase(){
        abstract fun exerciseDao(): ExerciseDao
        abstract fun userEntityDao(): UserEntityDao
        abstract fun dailyTrackDao(): DailyTrackDao

        companion object {
            @Volatile
            private var Instance: GymmateEmbeddedDatabase? = null
            fun getDatabase(context: Context): GymmateEmbeddedDatabase {
                return Instance ?: synchronized(this) {
                    Room.databaseBuilder(context, GymmateEmbeddedDatabase::class.java, "gymmate_database")
                        .build()
                        .also {
                            Instance = it
                        }
                }
            }
        }

}