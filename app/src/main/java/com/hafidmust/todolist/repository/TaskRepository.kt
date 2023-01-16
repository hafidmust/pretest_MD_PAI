package com.hafidmust.todolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.database.TaskDao
import com.hafidmust.todolist.database.TaskRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskRepository(application : Application) {
    private val mTaskDao : TaskDao
    private val executorsService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        mTaskDao = db.taskDao()
    }

    fun getAllTask() : LiveData<List<Task>> = mTaskDao.getAllTask()

    fun insert(task : Task){
        executorsService.execute { mTaskDao.insertTask(task) }
    }

    fun delete(task : Task){
        executorsService.execute { mTaskDao.deleteTask(task) }
    }

    fun update(task : Task){
        executorsService.execute { mTaskDao.updateTask(task) }
    }

    fun setFinishTask(task : Task, newState : Boolean){
        task.isFinish = newState
        executorsService.execute { mTaskDao.setFinishTask(task) }
    }
}