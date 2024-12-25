package com.example.diary.domain

import androidx.lifecycle.LiveData
import com.example.diary.data.storage.TaskDao
import com.example.diary.data.storage.TaskDb
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskMapper: TaskMapper
) {
    fun getAllTasks(): LiveData<List<TaskDb>> {
        return taskDao.getAll()
    }

    fun getTaskById(id: Long): LiveData<TaskDb> {
        return taskDao.get(id)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insert(taskMapper.mapToData(task))
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(taskMapper.mapToData(task))
    }
}