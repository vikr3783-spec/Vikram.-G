package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.DateNightEntity
import com.example.ui.components.CategoryChip
import com.example.ui.components.HeartRatingBar
import com.example.ui.components.RomanticCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmethystPrime
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintSuccess
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeDark
import com.example.ui.theme.RosePrimeGlow
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel

@Composable
fun DateNightScreen(
    viewModel: LovePrimeViewModel,
    modifier: Modifier = Modifier
) {
    val dateNights by viewModel.dateNights.collectAsState()
    val rouletteResult by viewModel.rouletteResult.collectAsState()
    val isSpinning by viewModel.isSpinning.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }
    var completeDateDialogItem by remember { mutableStateOf<DateNightEntity?>(null) }

    val filterOptions = listOf("All", "Cozy Home", "Dining", "Outdoor", "Playful", "Adventure", "Completed")

    val filteredList = dateNights.filter { date ->
        when (selectedFilter) {
            "All" -> true
            "Completed" -> date.isCompleted
            else -> date.category.contains(selectedFilter, ignoreCase = true)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("date_night_screen"),
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
                            text = "DATE NIGHT PLANNER",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = RomanticWhite
                        )
                        Text(
                            text = "Prime sparks & romantic adventures",
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
                            text = "${dateNights.count { it.isCompleted }}/${dateNights.size} Completed",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = GoldAccent
                        )
                    }
                }
            }

            // Interactive Date Night Roulette Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("date_roulette_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetCard),
                    border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(GoldAccent, RosePrimeGlow)))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Casino,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "DATE NIGHT ROULETTE",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    ),
                                    color = GoldAccent
                                )
                            }
                            Text(
                                text = "Can't decide?",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (rouletteResult != null) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = VelvetSurfaceVariant,
                                border = BorderStroke(1.dp, CardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = rouletteResult!!.category,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = RosePrimeLight
                                        )
                                        Text(
                                            text = rouletteResult!!.costIndicator,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = GoldAccent
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = rouletteResult!!.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = RomanticWhite
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = rouletteResult!!.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMuted
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        } else {
                            Text(
                                text = "Spin the wheel to discover an unexpected romantic adventure together!",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        Button(
                            onClick = { viewModel.spinDateNightRoulette() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("spin_roulette_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSpinning) RosePrimeDark else GoldAccent,
                                contentColor = Color.Black
                            ),
                            enabled = !isSpinning && dateNights.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Casino,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isSpinning) "Spinning Romantic Sparks..." else "SPIN FOR TONIGHT'S DATE",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold)
                            )
                        }
                    }
                }
            }

            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(filterOptions) { filter ->
                        CategoryChip(
                            text = filter,
                            isSelected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            modifier = Modifier.testTag("filter_$filter")
                        )
                    }
                }
            }

            // Date Night Cards List
            if (filteredList.isEmpty()) {
                item {
                    RomanticCard(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No dates found in this category. Tap '+' to create one!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted
                            )
                        }
                    }
                }
            } else {
                items(filteredList, key = { it.id }) { dateItem ->
                    DateNightCard(
                        dateNight = dateItem,
                        onToggleCompleted = {
                            if (!dateItem.isCompleted) {
                                completeDateDialogItem = dateItem
                            } else {
                                viewModel.toggleDateNightCompleted(dateItem)
                            }
                        },
                        onDelete = { viewModel.deleteDateNight(dateItem) }
                    )
                }
            }
        }

        // FAB to Add Date
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 96.dp)
                .testTag("add_date_fab"),
            containerColor = RosePrime,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Date Idea")
        }
    }

    // Add Date Dialog
    if (showAddDialog) {
        AddDateDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, desc, category, cost ->
                viewModel.addDateNight(title, desc, category, cost)
                showAddDialog = false
            }
        )
    }

    // Complete Date & Rate Dialog
    completeDateDialogItem?.let { item ->
        CompleteDateDialog(
            dateNight = item,
            onDismiss = { completeDateDialogItem = null },
            onConfirm = { rating, notes ->
                viewModel.toggleDateNightCompleted(item, rating, notes)
                completeDateDialogItem = null
            }
        )
    }
}

@Composable
fun DateNightCard(
    dateNight: DateNightEntity,
    onToggleCompleted: () -> Unit,
    onDelete: () -> Unit
) {
    RomanticCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("date_night_card_${dateNight.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryChip(text = dateNight.category)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = dateNight.costIndicator,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = GoldAccent
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = TextMuted.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dateNight.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = RomanticWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dateNight.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )

            if (dateNight.isCompleted) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = MintSuccess,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Completed ${dateNight.completedDate ?: ""}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MintSuccess
                        )
                    }

                    if (dateNight.rating > 0) {
                        HeartRatingBar(rating = dateNight.rating)
                    }
                }

                if (dateNight.notes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "\"${dateNight.notes}\"",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onToggleCompleted() },
                color = if (dateNight.isCompleted) VelvetSurfaceVariant else RosePrime.copy(alpha = 0.15f),
                border = BorderStroke(
                    1.dp,
                    if (dateNight.isCompleted) CardBorder else RosePrime.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (dateNight.isCompleted) Icons.Default.CheckCircle else Icons.Default.Favorite,
                        contentDescription = null,
                        tint = if (dateNight.isCompleted) MintSuccess else RosePrimeLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (dateNight.isCompleted) "Completed (Tap to undo)" else "Mark Date Completed",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (dateNight.isCompleted) MintSuccess else RosePrimeLight
                    )
                }
            }
        }
    }
}

@Composable
fun AddDateDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, desc: String, category: String, cost: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Cozy Home") }
    var cost by remember { mutableStateOf("$$") }

    val categories = listOf("Cozy Home", "Dining", "Outdoor", "Playful", "Adventure")
    val costs = listOf("$", "$$", "$$$")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Plan a New Date Night", color = RomanticWhite) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Date Title") },
                    placeholder = { Text("e.g., Midnight Rooftop Stargazing") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Details & Ideas") },
                    placeholder = { Text("What makes this date special?") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )

                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        CategoryChip(
                            text = cat,
                            isSelected = category == cat,
                            onClick = { category = cat }
                        )
                    }
                }

                Text(
                    text = "Estimated Cost",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    costs.forEach { c ->
                        CategoryChip(
                            text = c,
                            isSelected = cost == c,
                            onClick = { cost = c }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onAdd(title, description, category, cost) },
                colors = ButtonDefaults.buttonColors(containerColor = RosePrime),
                enabled = title.isNotBlank()
            ) {
                Text("Add Date")
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

@Composable
fun CompleteDateDialog(
    dateNight: DateNightEntity,
    onDismiss: () -> Unit,
    onConfirm: (rating: Int, notes: String) -> Unit
) {
    var rating by remember { mutableStateOf(5) }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("How was your date?", color = RomanticWhite) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "\"${dateNight.title}\"",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = RosePrimeLight
                )

                Text(
                    text = "Rate your romantic connection:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )

                HeartRatingBar(
                    rating = rating,
                    onRatingChanged = { rating = it }
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Favorite memory or inside joke?") },
                    placeholder = { Text("Write a quick note for your memory timeline...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(rating, notes) },
                colors = ButtonDefaults.buttonColors(containerColor = MintSuccess)
            ) {
                Text("Save & Complete", color = Color.Black, fontWeight = FontWeight.Bold)
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
