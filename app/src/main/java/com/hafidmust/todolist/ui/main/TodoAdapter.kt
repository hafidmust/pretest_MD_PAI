package com.hafidmust.todolist.ui.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hafidmust.todolist.R
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.databinding.ItemTaskBinding

class TodoAdapter(private val clickListener : IClickListener) : ListAdapter<Task, TodoAdapter.ViewHolder>(TodoComparator()) {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTaskBinding.bind(itemView)

        fun bind(data: Task) {
            with(binding) {
                tvItemTitle.text = data.title
                tvItemDate.text = data.date
                tvItemDescription.text = data.description

                root.setOnClickListener {
                    clickListener.onClicked(data)
                }

            }
            when(data.isFinish){
                true -> {
                    binding.tvItemTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.tvItemDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.tvItemDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                false ->{
                    binding.tvItemTitle.paintFlags = Paint.ANTI_ALIAS_FLAG
                    binding.tvItemDate.paintFlags = Paint.ANTI_ALIAS_FLAG
                    binding.tvItemDescription.paintFlags = Paint.ANTI_ALIAS_FLAG
                }
            }
        }

    }

    class TodoComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title
        }

    }

    interface IClickListener {
        fun onClicked(data : Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}