package com.example.diary.presentation.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.diary.domain.Task
import com.example.diary.domain.TaskMapper
import com.example.diary.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskMapper
) : ViewModel() {
    val allTasks: LiveData<List<Task>> = taskRepository.getAllTasks().map { taskDbList ->
        taskDbList.map { taskMapper.mapToDomain(it) }
    }

    fun getTaskById(id: Long): LiveData<Task?> {
        return taskRepository.getTaskById(id).map { taskDb ->
            taskDb.let { taskMapper.mapToDomain(it) }
        }

    }

    private fun insert(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
            Log.d("TaskViewModel", "Сохраняем задачу в формате JSON: ${taskMapper.taskToJson(task)}")
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun saveTask(taskName: String, taskDescription: String, selectedDateMillis: Long?, selectedTimeMillis: Long?): Boolean {
        if (taskName.isBlank() || selectedDateMillis == null || selectedTimeMillis == null) {
            return false
        }

        val dateCalendar = Calendar.getInstance().apply {
            timeInMillis = selectedDateMillis
        }

        val timeCalendar = Calendar.getInstance().apply {
            timeInMillis = selectedTimeMillis
        }

        dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
        dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
        val dateEndMillis = dateCalendar.timeInMillis + (60 * 60 * 1000)

        val task = Task(
            name = taskName,
            description = taskDescription,
            dateStart = dateCalendar.timeInMillis,
            dateFinish = dateEndMillis
        )
        this.insert(task)
        return true
    }

    fun filterAndSortTasksByDate(allTasks: List<Task>, selectedDateMillis: Long): List<Task> {
        val dateStartAtMidnight = selectedDateMillis - selectedDateMillis % (24 * 60 * 60 * 1000)
        return allTasks.filter { task ->
            val taskDate = task.dateStart ?: 0L
            taskDate >= dateStartAtMidnight && taskDate < dateStartAtMidnight + (24 * 60 * 60 * 1000)
        }.sortedBy { it.dateStart ?: 0L }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTaskTime(task: Task): String {
        val dateStart = task.dateStart ?: 0L
        val dateEnd = task.dateFinish ?: 0L

        val startDateTime = Timestamp(dateStart).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        val endDateTime = Timestamp(dateEnd).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

        return "${startDateTime.hour} - ${endDateTime.hour}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateMillis: Long): String {
        if (dateMillis == 0L) return "not set"
        val instant = Instant.ofEpochMilli(dateMillis)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return dateTime.format(formatter)
    }
}
