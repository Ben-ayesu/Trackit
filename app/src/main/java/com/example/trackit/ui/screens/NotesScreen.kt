package com.example.trackit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesScreen() {

    val context = LocalContext.current
    val elements = MutableList(100) { it }

    Column() {
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
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .padding(4.dp)
                        .clickable {
                            Toast
                                .makeText(context, "Item $elements", Toast.LENGTH_SHORT)
                                .show()
                        },
                ) {
                    Text(text = "Element ${elements[it]}")
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    NotesScreen()
}