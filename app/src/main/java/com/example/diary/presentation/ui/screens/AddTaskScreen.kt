package com.example.diary.presentation.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diary.R
import com.example.diary.presentation.ui.TaskViewModel
import com.example.diary.presentation.ui.components.MyDateInput
import com.example.diary.presentation.ui.components.MyTimeInput

@Composable
fun AddTaskScreen(navController: NavHostController, viewModel: TaskViewModel) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedTimeMillis by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = taskName, onValueChange = { taskName = it }, label = { Text(
            stringResource(
                R.string.name_task
            )
        ) })
        TextField(value = taskDescription, onValueChange = { taskDescription = it }, label = { Text(
            stringResource(R.string.description_task)
        ) })
        MyDateInput { selectedDate -> selectedDateMillis = selectedDate }
        MyTimeInput { selectedTime -> selectedTimeMillis = selectedTime }

        Button(onClick = {
            if (viewModel.saveTask(taskName, taskDescription, selectedDateMillis, selectedTimeMillis)) {
                taskName = ""
                taskDescription = ""
                selectedDateMillis = null
                selectedTimeMillis = null
                navController.navigate("calendar")
            }
        }) {
            Text(stringResource(R.string.save))
        }
    }
}