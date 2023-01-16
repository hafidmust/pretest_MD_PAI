package com.hafidmust.todolist.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.repository.TaskRepository

class TaskAddUpdateViewModel(application: Application) : ViewModel() {

    private val taskRepository: TaskRepository = TaskRepository(application)

    fun insert(task: Task) {
        taskRepository.insert(task)
    }

    fun update(task: Task) {
        taskRepository.update(task)
    }

    fun delete(task: Task) {
        taskRepository.delete(task)
    }

    fun setFinish(task: Task, newState: Boolean) {
        taskRepository.setFinishTask(task, newState)
    }

}