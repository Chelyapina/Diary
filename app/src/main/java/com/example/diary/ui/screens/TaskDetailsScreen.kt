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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diary.ui.TaskViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
                task?.let {
                    LabeledText(label = "Название", text = it.name)
                    LabeledText(label = "Описание", text = it.description)
                    it.dateStart?.let { it1 ->
                        formatDateTime(
                            it1
                        )
                    }?.let { it2 -> LabeledText(label = "Дата и время начала", text = it2) }
                    it.dateFinish?.let { it1 -> formatDateTime(it1) }?.let { it2 ->
                        LabeledText(
                            label = "Дата и время окончания",
                            text = it2
                        )
                    }
                }
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

@Composable
fun LabeledText(label: String, text: String) {
    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Text(text = text, modifier = Modifier.padding(bottom = 8.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateMillis: Long): String {
    if (dateMillis == 0L) return "not set"
    val instant = Instant.ofEpochMilli(dateMillis)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return dateTime.format(formatter)
}