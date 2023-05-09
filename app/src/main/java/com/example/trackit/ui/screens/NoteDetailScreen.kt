package com.example.trackit.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackit.Utils
import com.example.trackit.ui.Viewmodels.DetailUiState
import com.example.trackit.ui.Viewmodels.DetailViewModel
import com.example.trackit.ui.theme.TrackitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    noteId: String,
    onNavigate: () -> Unit
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isFormsNotBlank = detailUiState.note.isNotBlank() &&
            detailUiState.title.isNotBlank()

    val selectedColor by animateColorAsState(
        targetValue = Utils.colors[detailUiState.colorIndex],
        label = "",
    )

    val isNoteIdNotBlank = noteId.isNotBlank()
    val icon = if (isNoteIdNotBlank) Icons.Default.Refresh else Icons.Default.Check

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = Unit) {
        if (isNoteIdNotBlank) {
            detailViewModel?.getNote(noteId)
        } else {
            detailViewModel?.resetState()
        }
        if (detailUiState.noteAddedStatus) {
            detailViewModel?.resetState()
            onNavigate.invoke()
        }
        if (detailUiState.updateNoteStatus) {
            detailViewModel?.resetNoteAddedStatus()
            onNavigate.invoke()
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(visible = true) {
                FloatingActionButton(
                    onClick = {
                        if (isNoteIdNotBlank) {
                            detailViewModel?.updateNote(noteId)
                        } else {
                            detailViewModel?.addNote()
                            onNavigate.invoke()
                        }
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(selectedColor)
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 8.dp
                )
            ) {
                itemsIndexed(Utils.colors) { colorIndex, color ->
                    ColorItem(color = color) {
                        detailViewModel?.onColorChange(colorIndex)
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                value = detailUiState.title,
                onValueChange = {
                    detailViewModel?.onTitleChange(it)
                },
                label = { Text(text = "Title") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                value = detailUiState.note,
                onValueChange = {
                    detailViewModel?.onNoteChange(it)
                },
                label = { Text(text = "Note") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    onClick: () -> Unit,
) {
    Surface(
        color = color,
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(36.dp)
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(2.dp, Color.Black)
    ) {

    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevDetailScreen() {
    TrackitTheme {
        DetailScreen(detailViewModel = null, noteId = "") {
        }
    }
}
