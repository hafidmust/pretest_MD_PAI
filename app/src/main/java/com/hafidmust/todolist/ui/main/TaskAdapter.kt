package com.hafidmust.todolist.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.databinding.ItemTaskBinding
import com.hafidmust.todolist.helper.TaskDiffCallback
import com.hafidmust.todolist.ui.insert.TaskAddUpdateActivity

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val listTask = ArrayList<Task>()
    fun setListTasks(listNotes: List<Task>) {
        val diffCallback = TaskDiffCallback(this.listTask, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTask.clear()
        this.listTask.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                tvItemTitle.text = task.title
                tvItemDate.text = task.date
                tvItemDescription.text = task.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, TaskAddUpdateActivity::class.java)
                    intent.putExtra(TaskAddUpdateActivity.EXTRA_TASK, task)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context))
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(listTask[position])
    }

    override fun getItemCount(): Int {
        return listTask.size
    }
}