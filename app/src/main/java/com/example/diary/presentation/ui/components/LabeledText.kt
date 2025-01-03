package com.example.diary.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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