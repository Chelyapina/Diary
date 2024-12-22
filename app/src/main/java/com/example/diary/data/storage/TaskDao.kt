package com.example.diary.data.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Task)

    @Update
    suspend fun update(note: Task)

    @Delete
    suspend fun delete(note: Task)

    @Query("SELECT * FROM tasks_table WHERE id = :id")
    fun get(id: Long) : LiveData<Task>

    @Query("SELECT * FROM tasks_table ORDER BY id DESC")
    fun getAll() : LiveData<List<Task>>
}