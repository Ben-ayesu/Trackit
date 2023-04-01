package com.example.trackit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackit.components.NotesCard

@Composable
fun NotesScreen() {

    val context = LocalContext.current
    val elements = MutableList(100) { it }

    Column {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Create a note",
            fontSize = 32.sp
        )
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            items(elements) {
                NotesCard()
            }
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    NotesScreen()
}