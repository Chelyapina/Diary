package com.example.diary.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.data.storage.TaskDb
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

        this.insert(TaskDb(
            name = taskName,
            description = taskDescription,
            dateStart = dateCalendar.timeInMillis,
            dateFinish = dateEndMillis
        ))
        return true
    }

    fun filterAndSortTasksByDate(allTasks: List<TaskDb>, selectedDateMillis: Long): List<TaskDb> {
        val dateStartAtMidnight = selectedDateMillis - selectedDateMillis % (24 * 60 * 60 * 1000)
        return allTasks.filter { task ->
            val taskDate = task.dateStart ?: 0L
            taskDate >= dateStartAtMidnight && taskDate < dateStartAtMidnight + (24 * 60 * 60 * 1000)
        }.sortedBy { it.dateStart ?: 0L }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTaskTime(task: TaskDb): String {
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
