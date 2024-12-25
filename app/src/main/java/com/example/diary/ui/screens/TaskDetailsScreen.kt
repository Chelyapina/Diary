package com.example.diary.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TaskDetailsScreen(navController: NavHostController, taskId: String?) {
    Text("Подробности о задаче $taskId")
}