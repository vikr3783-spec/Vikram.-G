package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.local.entity.CheckInEntity
import com.example.ui.components.CategoryChip
import com.example.ui.components.RomanticCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmethystPrime
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintSuccess
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeDark
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel

@Composable
fun CheckInScreen(
    viewModel: LovePrimeViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.coupleProfile.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val pastCheckIns by viewModel.checkIns.collectAsState()

    var partner1Answer by remember { mutableStateOf("") }
    var partner2Answer by remember { mutableStateOf("") }
    var isRevealed by remember { mutableStateOf(false) }
    var isSavedNotification by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("check_in_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Banner / Intro
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PRIME TIME SYNC",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = RomanticWhite
                    )
                    Text(
                        text = "5-minute daily intimacy check-in",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = VelvetSurfaceVariant,
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = RosePrimeLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${pastCheckIns.size} Synced",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = RomanticWhite
                        )
                    }
                }
            }
        }

        // Current Question Spark Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("current_question_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetCard),
                border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(RosePrimeLight, AmethystPrime)))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CategoryChip(
                            text = currentQuestion.first,
                            isSelected = true
                        )

                        IconButton(
                            onClick = {
                                viewModel.nextQuestion()
                                partner1Answer = ""
                                partner2Answer = ""
                                isRevealed = false
                            },
                            modifier = Modifier.testTag("shuffle_question_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Shuffle Question",
                                tint = RosePrimeLight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "\"${currentQuestion.second}\"",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        ),
                        color = RomanticWhite
                    )
                }
            }
        }

        // Response Input Form
        item {
            RomanticCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("response_form_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Privacy Reveal Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isRevealed) "Answers Revealed" else "Mystery Mode (Hidden)",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isRevealed) MintSuccess else GoldAccent
                        )

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = VelvetSurfaceVariant,
                            modifier = Modifier
                                .clickable { isRevealed = !isRevealed }
                                .testTag("toggle_reveal_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle Reveal",
                                    tint = RosePrimeLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isRevealed) "Hide" else "Reveal Together",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = RomanticWhite
                                )
                            }
                        }
                    }

                    // Partner 1 Answer
                    Text(
                        text = "${profile.partner1Name}'s Response",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = RosePrimeLight
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    if (!isRevealed && partner1Answer.isNotEmpty()) {
                        HiddenAnswerMask(name = profile.partner1Name)
                    } else {
                        OutlinedTextField(
                            value = partner1Answer,
                            onValueChange = { partner1Answer = it },
                            placeholder = { Text("Share what's in your heart...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("partner1_answer_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RosePrime,
                                unfocusedBorderColor = CardBorder,
                                focusedTextColor = RomanticWhite,
                                unfocusedTextColor = RomanticWhite,
                                cursorColor = RosePrime
                            ),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Partner 2 Answer
                    Text(
                        text = "${profile.partner2Name}'s Response",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = GoldAccent
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    if (!isRevealed && partner2Answer.isNotEmpty()) {
                        HiddenAnswerMask(name = profile.partner2Name)
                    } else {
                        OutlinedTextField(
                            value = partner2Answer,
                            onValueChange = { partner2Answer = it },
                            placeholder = { Text("Your turn, write your answer...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("partner2_answer_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = CardBorder,
                                focusedTextColor = RomanticWhite,
                                unfocusedTextColor = RomanticWhite,
                                cursorColor = GoldAccent
                            ),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (partner1Answer.isNotBlank() || partner2Answer.isNotBlank()) {
                                viewModel.submitDailyCheckIn(partner1Answer, partner2Answer)
                                isSavedNotification = true
                                isRevealed = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("save_check_in_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosePrime,
                            contentColor = Color.White
                        ),
                        enabled = partner1Answer.isNotBlank() || partner2Answer.isNotBlank()
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Today's Check-In",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    if (isSavedNotification) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MintSuccess, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Synced to your relationship timeline! ✨",
                                style = MaterialTheme.typography.bodySmall,
                                color = MintSuccess
                            )
                        }
                    }
                }
            }
        }

        // Check-In History
        item {
            SectionHeader(
                title = "PAST PRIME CHECK-INS",
                subtitle = "Look back on answers and moments you shared"
            )
        }

        if (pastCheckIns.isEmpty()) {
            item {
                RomanticCard(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No past check-ins yet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted
                            )
                            Text(
                                text = "Complete today's prompt above to begin your archive!",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        } else {
            items(pastCheckIns) { item ->
                PastCheckInCard(
                    checkIn = item,
                    partner1Name = profile.partner1Name,
                    partner2Name = profile.partner2Name
                )
            }
        }
    }
}

@Composable
fun HiddenAnswerMask(name: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VelvetSurfaceVariant,
        border = BorderStroke(1.dp, CardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = RosePrimeLight,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$name has locked their answer! Tap 'Reveal Together' to unveil.",
                style = MaterialTheme.typography.bodySmall,
                color = RomanticWhite
            )
        }
    }
}

@Composable
fun PastCheckInCard(
    checkIn: CheckInEntity,
    partner1Name: String,
    partner2Name: String
) {
    RomanticCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryChip(text = checkIn.category)
                Text(
                    text = checkIn.dateString,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = checkIn.question,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = RomanticWhite
            )

            if (checkIn.partner1Answer.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(VelvetSurfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(
                        text = partner1Name,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = RosePrimeLight
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = checkIn.partner1Answer,
                        style = MaterialTheme.typography.bodySmall,
                        color = RomanticWhite
                    )
                }
            }

            if (checkIn.partner2Answer.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(VelvetSurfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(
                        text = partner2Name,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = GoldAccent
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = checkIn.partner2Answer,
                        style = MaterialTheme.typography.bodySmall,
                        color = RomanticWhite
                    )
                }
            }
        }
    }
}
