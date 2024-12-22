package com.example.diary.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "date_start")
    var dateStart: Long = 0L,
    @ColumnInfo(name = "date_finish")
    var dateFinish: Long = 0L,
    @ColumnInfo(name = "task_name")
    var name: String = "",
    @ColumnInfo(name = "task_description")
    var description: String = ""
)

//{
//    “id”:1,
//    “date_start”:”147600000”,
//    “date_finish”:”147610000”,
//    “name”:”My task”,
//    “description”:”Описание моего дела”
//} (date_start, date_finish имеют тип timestamp)