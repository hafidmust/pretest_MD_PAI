package com.hafidmust.todolist.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.hafidmust.todolist.R
import com.hafidmust.todolist.database.Task
import com.hafidmust.todolist.databinding.ActivityTaskAddUpdateBinding
import com.hafidmust.todolist.helper.DateHelper
import com.hafidmust.todolist.helper.ViewModelFactory

class TaskAddUpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK = "extra_task"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

    }

    private lateinit var taskAddUpdateViewModel: TaskAddUpdateViewModel

    private var _activityTaskAddUpdateBinding: ActivityTaskAddUpdateBinding? = null
    private val binding get() = _activityTaskAddUpdateBinding

    private var isEdit = false
    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityTaskAddUpdateBinding = ActivityTaskAddUpdateBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        taskAddUpdateViewModel = obtainViewModel(this@TaskAddUpdateActivity)
        task = intent.getParcelableExtra(EXTRA_TASK)

        var statusFinish = task?.isFinish
        if (statusFinish != null) {
            binding?.cbFinish?.isChecked = statusFinish
        }
        setStatusFinish(statusFinish)
        binding?.cbFinish?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                setStatusFinish(true)
                taskAddUpdateViewModel.setFinish(task as Task,true)
            }else{
                setStatusFinish(false)
                taskAddUpdateViewModel.setFinish(task as Task,false)
            }

        }
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()




            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }
                else -> {
                    task.let { task ->
                        task?.title = title
                        task?.description = description

//                        if (statusFinish != null) {
//                            task?.isFinish = statusFinish
//                        }
                    }
                    if (isEdit) {
                        taskAddUpdateViewModel.update(task as Task)
//                        if (statusFinish != null) {
//                            taskAddUpdateViewModel.setFinish(task as Task, statusFinish)
//                        }
                        showToast(getString(R.string.changed))
                    } else {
                        task.let { task ->
                            task?.date = DateHelper.getCurrentDate()
                        }
                        taskAddUpdateViewModel.insert(task as Task)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }


        if (task != null) {
            isEdit = true
        } else {
            task = Task()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            binding?.cbFinish?.visibility = View.VISIBLE
            if (task != null) {
                task?.let { task ->
                    binding?.edtTitle?.setText(task.title)
                    binding?.edtDescription?.setText(task.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnSubmit?.text = btnTitle
    }

    private fun setStatusFinish(statusFinish: Boolean?) {
        binding?.cbFinish?.isChecked = statusFinish == true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): TaskAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TaskAddUpdateViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityTaskAddUpdateBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }


    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    taskAddUpdateViewModel.delete(task as Task)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}