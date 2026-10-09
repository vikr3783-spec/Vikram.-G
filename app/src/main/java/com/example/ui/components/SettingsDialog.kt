package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.CoupleProfile
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetSurfaceVariant

@Composable
fun SettingsDialog(
    profile: CoupleProfile,
    onDismiss: () -> Unit,
    onSave: (p1: String, p2: String, anniversaryMillis: Long, p1Ll: String, p2Ll: String, status: String) -> Unit
) {
    var p1Name by remember { mutableStateOf(profile.partner1Name) }
    var p2Name by remember { mutableStateOf(profile.partner2Name) }
    val initialDays = ((System.currentTimeMillis() - profile.anniversaryDateMillis) / (24L * 60 * 60 * 1000)).coerceAtLeast(1)
    var daysCountStr by remember { mutableStateOf(initialDays.toString()) }
    var p1LoveLanguage by remember { mutableStateOf(profile.partner1LoveLanguage) }
    var p2LoveLanguage by remember { mutableStateOf(profile.partner2LoveLanguage) }
    var statusMessage by remember { mutableStateOf(profile.customStatusMessage) }

    val loveLanguages = listOf(
        "Words of Affirmation",
        "Quality Time",
        "Physical Touch",
        "Acts of Service",
        "Receiving Gifts"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Couple Profile & Settings", color = RomanticWhite) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = p1Name,
                    onValueChange = { p1Name = it },
                    label = { Text("Partner 1 Name") },
                    modifier = Modifier.fillMaxWidth().testTag("partner1_name_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = p2Name,
                    onValueChange = { p2Name = it },
                    label = { Text("Partner 2 Name") },
                    modifier = Modifier.fillMaxWidth().testTag("partner2_name_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = daysCountStr,
                    onValueChange = { if (it.all { char -> char.isDigit() }) daysCountStr = it },
                    label = { Text("Days Together So Far") },
                    placeholder = { Text("e.g., 101") },
                    modifier = Modifier.fillMaxWidth().testTag("days_together_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    text = "$p1Name's Love Language",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(loveLanguages) { ll ->
                        CategoryChip(
                            text = ll,
                            isSelected = p1LoveLanguage == ll,
                            onClick = { p1LoveLanguage = ll }
                        )
                    }
                }

                Text(
                    text = "$p2Name's Love Language",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(loveLanguages) { ll ->
                        CategoryChip(
                            text = ll,
                            isSelected = p2LoveLanguage == ll,
                            onClick = { p2LoveLanguage = ll }
                        )
                    }
                }

                OutlinedTextField(
                    value = statusMessage,
                    onValueChange = { statusMessage = it },
                    label = { Text("Couple Motto / Status") },
                    placeholder = { Text("e.g., Always in our Prime together ✨") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Official Release Info
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = VelvetSurfaceVariant,
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "PLAY STORE RELEASE",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = GoldAccent
                            )
                            Text(
                                text = "v1.0.29 (Build 29)",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = RosePrimeLight
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "📅 Release Date: October 29",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = RomanticWhite
                        )
                        Text(
                            text = "✨ Zero-Permission Private Architecture • Production Verified",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val days = daysCountStr.toLongOrNull() ?: initialDays
                    val newAnniversaryMillis = System.currentTimeMillis() - (days * 24L * 60L * 60L * 1000L)
                    onSave(p1Name, p2Name, newAnniversaryMillis, p1LoveLanguage, p2LoveLanguage, statusMessage)
                },
                colors = ButtonDefaults.buttonColors(containerColor = RosePrime),
                modifier = Modifier.testTag("save_settings_button")
            ) {
                Text("Save Profile")
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
