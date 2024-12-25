package com.example.diary.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diary.data.storage.TaskDb
import com.example.diary.ui.TaskViewModel
import com.example.diary.ui.components.MyDateInput
import com.example.diary.ui.components.MyTimeInput
import java.util.Calendar

@Composable
fun AddTaskScreen(navController: NavHostController, viewModel: TaskViewModel) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedTimeMillis by remember { mutableStateOf<Long?>(null) }

    Column(modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = taskName, onValueChange = { taskName = it }, label = { Text("Название задачи") })
        TextField(value = taskDescription, onValueChange = { taskDescription = it }, label = { Text("Описание задачи") })
        MyDateInput { selectedDate ->
            selectedDateMillis = selectedDate
        }
        MyTimeInput { selectedDate ->
            selectedTimeMillis = selectedDate
        }

        Button(onClick = {
            if (taskName.isNotBlank() && selectedDateMillis != null && selectedTimeMillis != null) {
                val dateCalendar = Calendar.getInstance().apply {
                    timeInMillis = selectedDateMillis!!
                }

                val timeCalendar = Calendar.getInstance().apply {
                    timeInMillis = selectedTimeMillis!!
                }

                dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
                dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
                val dateEndMillis = dateCalendar.timeInMillis + (60 * 60 * 1000)

                viewModel.insert(TaskDb(
                    name = taskName,
                    description = taskDescription,
                    dateStart = dateCalendar.timeInMillis,
                    dateFinish = dateEndMillis
                ))

                taskName = ""
                taskDescription = ""
                selectedDateMillis = null
                selectedTimeMillis = null
                navController.navigate("calendar")
            }
        }) {
            Text("Сохранить задачу")
        }
    }
}