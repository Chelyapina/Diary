package com.example.diary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.data.storage.TaskDb
import com.example.diary.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val allTasks: LiveData<List<TaskDb>> = taskRepository.getAllTasks()

    fun insert(task: TaskDb) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun get(taskId: Long): LiveData<TaskDb> {
        return taskRepository.getTaskById(taskId)
    }

    fun delete(task: TaskDb) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
