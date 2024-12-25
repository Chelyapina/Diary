package com.example.diary.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diary.presentation.ui.TaskViewModel
import com.example.diary.presentation.ui.components.MyDatePicker
import com.example.diary.presentation.ui.components.TwoLineListItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val allTasks by viewModel.allTasks.observeAsState(initial = emptyList())
    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FloatingActionButton(onClick = { navController.navigate("add_task") }) {
            Text("Добавить задачу")
        }

        MyDatePicker(onDateSelected = { date -> selectedDateMillis = date })

        val filteredAndSortedTasks = viewModel.filterAndSortTasksByDate(allTasks, selectedDateMillis)

        LazyColumn {
            items(filteredAndSortedTasks) { task ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("task_detail/${task.id}")
                        },
                    shape = MaterialTheme.shapes.medium
                ) {
                    val time = viewModel.formatTaskTime(task)
                    TwoLineListItem(time = time, taskName = task.name)
                }
            }
        }
    }
}
