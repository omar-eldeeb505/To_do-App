package com.route.todoappc40gsat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.todoappc40gsat.database.dao.TaskDao
import com.route.todoappc40gsat.database.model.Task
import com.route.todoappc40gsat.database.typeConverters.DateConverter

@Database(entities = [Task::class], version = 1) // update
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        // 1-
        private var INSTANCE: TaskDatabase? = null
        private const val DATA_BASE_NAME = "Tasks Database"
        fun getInstance(context: Context): TaskDatabase {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(context, TaskDatabase::class.java, DATA_BASE_NAME)
                    .fallbackToDestructiveMigration() // Migrations
                    .allowMainThreadQueries() // Coroutines
                    .build()
            // Main Thread -> Handle Navigation and Handle User Interactions
            return INSTANCE!!
        }
    }

}
