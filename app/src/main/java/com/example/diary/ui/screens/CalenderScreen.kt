package com.example.diary.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diary.ui.TaskViewModel
import com.example.diary.ui.components.DatePicker


@Composable
fun CalendarScreen(navController: NavHostController,
                   viewModel: TaskViewModel = hiltViewModel<TaskViewModel>()
) {

    FloatingActionButton(onClick = { navController.navigate("add_task") }) {
        Text("Добавить задачу")
    }

    val allTasks by viewModel.allTasks.observeAsState(initial = emptyList())

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    Column(modifier = Modifier.padding(16.dp)) {
        DatePicker(onDateSelected = { date ->
            selectedDateMillis = date
        })

        val filteredTasks = allTasks.filter { task ->
            val taskDate = task.dateStart
            val dateStartAtMidnight =
                selectedDateMillis - selectedDateMillis % (24 * 60 * 60 * 1000)
            taskDate >= dateStartAtMidnight && taskDate < dateStartAtMidnight + (24 * 60 * 60 * 1000)
        }

        LazyColumn {
            items(filteredTasks) { task ->
                Log.d("TAG", "Task: ${task.name}")
                Text(text = "${task.name} - ${task.description}")
            }
        }

    }
}
