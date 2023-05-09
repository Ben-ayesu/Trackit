package com.example.trackit.ui.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackit.models.Notes
import com.example.trackit.repository.Resources
import com.example.trackit.ui.Viewmodels.HomeUiState
import com.example.trackit.ui.Viewmodels.HomeViewModel
import com.google.firebase.Timestamp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navtoNotesDetailPage: () -> Unit,
    navtoLoginPage: () -> Unit
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }

    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotes()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navtoNotesDetailPage.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navtoLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(
                        "Notes Home",
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }

                is Resources.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            homeUiState.notesList.data ?: emptyList()
                        ) { note ->
                            NotesItem(
                                notes = note,
                                onLongClick = {
                                    openDialog = true
                                    selectedNote = note
                                },
                            ) {
                                onNoteClick.invoke(note.documentId)
                            }
                        }
                    }
                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = { Text(text = "Delete Note?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedNote?.documentId?.let {
                                            homeViewModel?.deleteNote(it)
                                        }
                                        openDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    ),
                                ) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            }
                        )
                    }
                }

                else -> {
                    Text(
                        text = homeUiState.notesList.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Red
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = homeViewModel?.hasUser
    ) {
        if (homeViewModel?.hasUser == false) {
            navtoLoginPage.invoke()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = notes.description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = formatDate(notes.timestamp),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp),
                maxLines = 1
            )
        }
    }
}

private fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("MM-dd-yy hh:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}

@Preview
@Composable
fun HomeScreenPrev() {
    HomeScreen(
        homeViewModel = null,
        onNoteClick = {},
        navtoNotesDetailPage = { /*TODO*/ }
    ) {

    }
}