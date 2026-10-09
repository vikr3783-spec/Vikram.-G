package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.MemoryEntity
import com.example.ui.components.CategoryChip
import com.example.ui.components.RomanticCard
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetDark
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MemoryVaultScreen(
    viewModel: LovePrimeViewModel,
    modifier: Modifier = Modifier
) {
    val memories by viewModel.memories.collectAsState()
    var selectedMoodFilter by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }

    val moodOptions = listOf("All", "Romantic", "Joyful", "Silly", "Cozy", "Adventure")

    val filteredList = memories.filter { memory ->
        if (selectedMoodFilter == "All") true
        else memory.mood.equals(selectedMoodFilter, ignoreCase = true)
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("memory_vault_screen"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MEMORY VAULT",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = RomanticWhite
                        )
                        Text(
                            text = "Cherished milestones & prime moments",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = VelvetSurfaceVariant,
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Text(
                            text = "${memories.size} Moments",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = GoldAccent
                        )
                    }
                }
            }

            // Mood Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(moodOptions) { mood ->
                        CategoryChip(
                            text = mood,
                            isSelected = selectedMoodFilter == mood,
                            onClick = { selectedMoodFilter = mood },
                            modifier = Modifier.testTag("mood_filter_$mood")
                        )
                    }
                }
            }

            // Memories List
            if (filteredList.isEmpty()) {
                item {
                    RomanticCard(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.PhotoAlbum,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "No memories in this mood yet",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextMuted
                                )
                                Text(
                                    text = "Tap '+' to record a beautiful milestone moment together.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            } else {
                items(filteredList, key = { it.id }) { memory ->
                    MemoryItemCard(
                        memory = memory,
                        onDelete = { viewModel.deleteMemory(memory) }
                    )
                }
            }
        }

        // FAB to Record Memory
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 96.dp)
                .testTag("add_memory_fab"),
            containerColor = RosePrime,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Memory")
        }
    }

    if (showAddDialog) {
        AddMemoryDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, note, location, mood ->
                viewModel.addMemory(title, note, location, mood)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun MemoryItemCard(
    memory: MemoryEntity,
    onDelete: () -> Unit
) {
    val dateFormatted = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(memory.dateMillis))

    RomanticCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("memory_card_${memory.id}"),
        border = if (memory.isPrimeMilestone) BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.8f))
        else BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryChip(text = memory.mood)

                    if (memory.isPrimeMilestone) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = GoldAccent.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, GoldBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "PRIME MILESTONE",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                    color = GoldAccent
                                )
                            }
                        }
                    }
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Memory",
                        tint = TextMuted.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = memory.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = RomanticWhite
            )

            if (memory.location.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = RosePrimeLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = memory.location,
                        style = MaterialTheme.typography.labelSmall,
                        color = RosePrimeLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = memory.note,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = RomanticWhite.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Captured on $dateFormatted",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
        }
    }
}

@Composable
fun AddMemoryDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, note: String, location: String, mood: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf("Romantic") }

    val moods = listOf("Romantic", "Joyful", "Silly", "Cozy", "Adventure")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Record a Shared Memory", color = RomanticWhite) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Moment Title") },
                    placeholder = { Text("e.g., Night Under The Stars") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location (Optional)") },
                    placeholder = { Text("e.g., Sunset Cliffs, Café") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Story & Feelings") },
                    placeholder = { Text("Write what made this moment unforgettable...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )

                Text(
                    text = "Mood of this Memory",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(moods) { m ->
                        CategoryChip(
                            text = m,
                            isSelected = mood == m,
                            onClick = { mood = m }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank() && note.isNotBlank()) onAdd(title, note, location, mood) },
                colors = ButtonDefaults.buttonColors(containerColor = RosePrime),
                enabled = title.isNotBlank() && note.isNotBlank()
            ) {
                Text("Save to Vault")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMuted)
            }
        },
        containerColor = VelvetCard
    )
}
