package com.example.diary.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diary.presentation.ui.TaskViewModel
import com.example.diary.presentation.ui.screens.AddTaskScreen
import com.example.diary.presentation.ui.screens.CalendarScreen
import com.example.diary.presentation.ui.screens.TaskDetailsScreen

@Composable
fun DiaryApp() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "calendar") {
        composable("calendar") { CalendarScreen(navController, viewModel) }
        composable("add_task") { AddTaskScreen(navController, viewModel) }
        composable("task_detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLongOrNull()
            if (taskId != null) {
                TaskDetailsScreen(navController, taskId, viewModel)
            }
        }
    }
}
