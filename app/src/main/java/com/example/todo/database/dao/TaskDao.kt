package com.route.todoappc40gsat.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.route.todoappc40gsat.database.model.Task
import java.util.Date

@Dao           // Data Access Object
interface TaskDao {
    // CRUD Operations  => Create - Read - Update - Delete
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM Todo")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM TODO WHERE date = :date")
    fun getTasksByDate(date: Date): List<Task>
}
