package com.example.diary.data.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(note: TaskDb)

    @Delete
    suspend fun delete(note: TaskDb)

    @Query("SELECT * FROM tasks_table WHERE id = :id")
    fun get(id: Long) : LiveData<TaskDb>

    @Query("SELECT * FROM tasks_table ORDER BY id DESC")
    fun getAll() : LiveData<List<TaskDb>>
}