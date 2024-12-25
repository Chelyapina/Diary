package com.example.diary.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable

@Composable
fun TwoLineListItem(time: String, taskName: String) {
    Column() {
        ListItem(
            headlineContent = {
                Text(time)
            },
            supportingContent = {
                Text(taskName)
            }
        )
        VerticalDivider()
    }
}