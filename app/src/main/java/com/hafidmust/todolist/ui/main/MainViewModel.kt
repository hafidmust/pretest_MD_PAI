package com.hafidmust.todolist.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.repository.TaskRepository

class MainViewModel(application : Application) : ViewModel() {
    private val taskRepository : TaskRepository = TaskRepository(application)

    fun getAllTasks() : LiveData<List<Task>> = taskRepository.getAllTask()
}