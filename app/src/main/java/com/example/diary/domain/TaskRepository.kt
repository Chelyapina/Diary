package com.example.diary.domain

import androidx.lifecycle.LiveData
import com.example.diary.data.storage.TaskDao
import com.example.diary.data.storage.TaskDb
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: TaskDb) = taskDao.insert(task)
    suspend fun deleteTask(task: TaskDb) = taskDao.delete(task)
    fun getAllTasks(): LiveData<List<TaskDb>> = taskDao.getAll()
    fun getTaskById(id: Long): LiveData<TaskDb> = taskDao.get(id)
}