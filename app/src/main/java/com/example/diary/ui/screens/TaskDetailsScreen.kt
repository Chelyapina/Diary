package com.example.diary.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diary.ui.TaskViewModel
import com.example.diary.ui.components.LabeledText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailsScreen(navController: NavHostController, taskId: Long, viewModel: TaskViewModel) {
    val task by viewModel.get(taskId).observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            task?.let {
                LabeledText(label = "Название", text = it.name)
                LabeledText(label = "Описание", text = it.description)

                LabeledText(
                    label = "Дата и время начала",
                    text = viewModel.formatDateTime(it.dateStart ?: 0L)
                )
                LabeledText(
                    label = "Дата и время окончания",
                    text = viewModel.formatDateTime(it.dateFinish ?: 0L)
                )

                Button(onClick = {
                    viewModel.delete(it)
                    navController.navigate("calendar")
                }) {
                    Text("Удалить")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    navController.navigate("calendar")
                }) {
                    Text("Назад")
                }
            }
        }
    }
}
