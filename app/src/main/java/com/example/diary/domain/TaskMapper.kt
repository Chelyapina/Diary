package com.example.diary.domain

import com.example.diary.data.storage.TaskDb
import com.google.gson.Gson
import javax.inject.Inject

interface TaskMapper {
    fun mapToDomain(taskDb: TaskDb): Task
    fun mapToData(task: Task): TaskDb
    fun taskToJson(task: Task): String
}

class TaskMapperImpl @Inject constructor() : TaskMapper{
    override fun mapToDomain(taskDb: TaskDb): Task {
        return Task(
            id = taskDb.id,
            dateStart = taskDb.dateStart ?: 0,
            dateFinish = taskDb.dateFinish ?: 0,
            name = taskDb.name,
            description = taskDb.description
        )
    }

    override fun mapToData(task: Task): TaskDb {
        return TaskDb(
            id = task.id,
            dateStart = task.dateStart,
            dateFinish = task.dateFinish,
            name = task.name,
            description = task.description
        )
    }

    private val gson = Gson()
    override fun taskToJson(task: Task): String {
        return gson.toJson(task)
    }
}