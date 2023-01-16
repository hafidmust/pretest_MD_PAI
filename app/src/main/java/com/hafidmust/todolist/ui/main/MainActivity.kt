package com.hafidmust.todolist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafidmust.todolist.R
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.databinding.ActivityMainBinding
import com.hafidmust.todolist.helper.ViewModelFactory
import com.hafidmust.todolist.ui.insert.TaskAddUpdateActivity

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllTasks().observe(this) { taskList ->
            if (taskList != null) {
                adapter.submitList(taskList)
            }
        }

        adapter = TodoAdapter(object : TodoAdapter.IClickListener {
            override fun onClicked(data: Task) {
                val intent = Intent(applicationContext, TaskAddUpdateActivity::class.java)
                intent.putExtra(TaskAddUpdateActivity.EXTRA_TASK, data)
                startActivity(intent)
            }
        })
        binding?.rvTasks?.layoutManager = LinearLayoutManager(this)
        binding?.rvTasks?.setHasFixedSize(true)
        binding?.rvTasks?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, TaskAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}