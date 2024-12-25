package com.example.diary

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.diary.ui.TaskViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diary.data.storage.TaskDb
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun DiaryApp(modifier: Modifier = Modifier,
             viewModel: TaskViewModel = hiltViewModel<TaskViewModel>()) {
    Log.d("TAG", "DiaryApp is Created")
    val allTasks by viewModel.allTasks.observeAsState(initial = emptyList())

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    Column(modifier = modifier.padding(16.dp)) {
        DatePickerSample(onDateSelected = { date ->
            selectedDateMillis = date
        })

        val filteredTasks = allTasks.filter { task ->
            val taskDate = task.dateStart
            val dateStartAtMidnight = selectedDateMillis - selectedDateMillis % (24 * 60 * 60 * 1000)
            taskDate >= dateStartAtMidnight && taskDate < dateStartAtMidnight + (24 * 60 * 60 * 1000)
        }

        LazyColumn {
            items(filteredTasks) { task ->
                Log.d("TAG", "Task: ${task.name}")
                Text(text = "${task.name} - ${task.description}")
            }
        }

        TextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            if (taskName.isNotBlank()) {
                viewModel.insert(TaskDb(name = taskName, description = taskDescription, dateStart = selectedDateMillis))
                taskName = ""
                taskDescription = ""
            }
        }) {
            Text("Add Task")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerSample(onDateSelected: (Long) -> Unit) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))

        LaunchedEffect(datePickerState.selectedDateMillis) {
            onDateSelected(datePickerState.selectedDateMillis ?: System.currentTimeMillis())
        }

        Text(
            "Selected date timestamp: ${datePickerState.selectedDateMillis ?: "no selection"}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
