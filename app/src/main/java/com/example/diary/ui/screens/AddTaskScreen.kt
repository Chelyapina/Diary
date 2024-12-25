package com.example.diary.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@Composable
fun AddTaskScreen(navController: NavHostController) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Column {
        TextField(value = taskName, onValueChange = { taskName = it }, label = { Text("Название задачи") })
        TextField(value = taskDescription, onValueChange = { taskDescription = it }, label = { Text("Описание задачи") })
        Button(onClick = {

        }) {
            Text("Сохранить задачу")
        }
    }
}