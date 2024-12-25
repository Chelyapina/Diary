package com.example.diary.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskDb::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao : TaskDao
}