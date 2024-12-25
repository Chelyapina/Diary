package com.example.diary

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diary.ui.screens.AddTaskScreen
import com.example.diary.ui.screens.CalendarScreen
import com.example.diary.ui.screens.TaskDetailsScreen

@Composable
fun DiaryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "calendar") {
        composable("calendar") { CalendarScreen(navController) }
        composable("add_task") { AddTaskScreen(navController) }
        composable("task_detail") { backStackEntry ->
            TaskDetailsScreen(navController, backStackEntry.arguments?.getString("taskId"))
        }
    }
}







//
//@Composable
//fun DiaryApp(modifier: Modifier = Modifier,
//             viewModel: TaskViewModel = hiltViewModel<TaskViewModel>()) {
//    Log.d("TAG", "DiaryApp is Created")
//    val allTasks by viewModel.allTasks.observeAsState(initial = emptyList())
//
//    var taskName by remember { mutableStateOf("") }
//    var taskDescription by remember { mutableStateOf("") }
//    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
//    Column(modifier = modifier.padding(16.dp)) {
//        DatePickerSample(onDateSelected = { date ->
//            selectedDateMillis = date
//        })
//
//        val filteredTasks = allTasks.filter { task ->
//            val taskDate = task.dateStart
//            val dateStartAtMidnight = selectedDateMillis - selectedDateMillis % (24 * 60 * 60 * 1000)
//            taskDate >= dateStartAtMidnight && taskDate < dateStartAtMidnight + (24 * 60 * 60 * 1000)
//        }
//
//        LazyColumn {
//            items(filteredTasks) { task ->
//                Log.d("TAG", "Task: ${task.name}")
//                Text(text = "${task.name} - ${task.description}")
//            }
//        }
//
//        TextField(
//            value = taskName,
//            onValueChange = { taskName = it },
//            label = { Text("Task Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        TextField(
//            value = taskDescription,
//            onValueChange = { taskDescription = it },
//            label = { Text("Task Description") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Button(onClick = {
//            if (taskName.isNotBlank()) {
//                viewModel.insert(TaskDb(name = taskName, description = taskDescription, dateStart = selectedDateMillis))
//                taskName = ""
//                taskDescription = ""
//            }
//        }) {
//            Text("Add Task")
//        }
//    }
//}
//

