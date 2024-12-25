package com.example.diary.presentation.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diary.R
import com.example.diary.presentation.ui.TaskViewModel
import com.example.diary.presentation.ui.components.LabeledText

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
                LabeledText(label = stringResource(R.string.name), text = it.name)
                LabeledText(label = stringResource(R.string.description), text = it.description)

                LabeledText(
                    label = stringResource(R.string.date_start),
                    text = viewModel.formatDateTime(it.dateStart ?: 0L)
                )
                LabeledText(
                    label = stringResource(R.string.date_finish),
                    text = viewModel.formatDateTime(it.dateFinish ?: 0L)
                )

                Button(onClick = {
                    viewModel.delete(it)
                    navController.navigate("calendar")
                }) {
                    Text(stringResource(R.string.delete))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    navController.navigate("calendar")
                }) {
                    Text(stringResource(R.string.back))
                }
            }
        }
    }
}
