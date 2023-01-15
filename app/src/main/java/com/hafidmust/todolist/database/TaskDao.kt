package com.hafidmust.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(task : Task)

    @Update
    fun updateTask(task : Task)

    @Delete
    fun deleteTask(task : Task)

    @Query("SELECT * from task ORDER BY id ASC")
    fun getAllTask() : LiveData<List<Task>>
}