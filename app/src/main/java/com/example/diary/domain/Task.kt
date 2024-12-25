package com.example.diary.domain

data class Task (
    var id: Long = 0L,
    var dateStart: Long = 0L,
    var dateFinish: Long = 0L,
    var name: String = "",
    var description: String = ""
)
