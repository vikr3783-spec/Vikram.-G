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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PrimeCalculator
import com.example.ui.components.PrimeBadge
import com.example.ui.components.RomanticCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmethystPrime
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.HeartRed
import com.example.ui.theme.MintSuccess
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeDark
import com.example.ui.theme.RosePrimeGlow
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetDark
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel

@Composable
fun HomeScreen(
    viewModel: LovePrimeViewModel,
    onNavigateToTab: (Int) -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.coupleProfile.collectAsState()
    val currentTime by viewModel.currentTimeMillis.collectAsState()
    val timeBreakdown = viewModel.calculateTimeBreakdown(profile.anniversaryDateMillis, currentTime)
    val daysCount = timeBreakdown.days
    val isPrime = PrimeCalculator.isPrime(daysCount)
    val nextPrime = PrimeCalculator.nextPrime(daysCount)
    val prevPrime = PrimeCalculator.previousPrime(daysCount) ?: 0L
    val daysUntilNext = nextPrime - daysCount

    val coupons by viewModel.coupons.collectAsState()
    val dateNights by viewModel.dateNights.collectAsState()
    val memories by viewModel.memories.collectAsState()
    val dailyQuote by viewModel.dailyQuote.collectAsState()

    val redeemedCouponsCount = coupons.count { it.isRedeemed }
    val completedDatesCount = dateNights.count { it.isCompleted }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Top Bar / Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(RosePrimeLight, RosePrimeDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Love Prime",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "LOVE PRIME",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp
                                ),
                                color = RosePrimeLight
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "™",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldAccent
                            )
                        }
                        Text(
                            text = "${profile.partner1Name} & ${profile.partner2Name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = VelvetSurfaceVariant,
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Text(
                            text = "Release 29",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = GoldAccent,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier.testTag("settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Hero Together Counter Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_together_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetCard),
                border = BorderStroke(
                    1.5.dp,
                    if (isPrime) Brush.horizontalGradient(listOf(GoldAccent, RosePrimeGlow))
                    else Brush.horizontalGradient(listOf(CardBorder, CardBorder))
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    RosePrimeDark.copy(alpha = 0.35f),
                                    VelvetCard
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Badge at top of card
                        PrimeBadge(days = daysCount, isPrime = isPrime)

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "TOGETHER IN LOVE",
                            style = MaterialTheme.typography.labelMedium.copy(
                                letterSpacing = 2.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Large Days Counter Display
                        Text(
                            text = "$daysCount",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 64.sp,
                                letterSpacing = (-1).sp
                            ),
                            color = if (isPrime) GoldAccent else RomanticWhite
                        )

                        Text(
                            text = if (daysCount == 1L) "DAY" else "DAYS",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 3.sp
                            ),
                            color = RosePrimeLight
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Live Time Breakdown ticker: Hours : Minutes : Seconds
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(VelvetSurfaceVariant.copy(alpha = 0.8f))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TimeTickerItem(value = timeBreakdown.hours, label = "HRS")
                            Text(text = ":", color = RosePrimeLight, fontWeight = FontWeight.Bold)
                            TimeTickerItem(value = timeBreakdown.minutes, label = "MIN")
                            Text(text = ":", color = RosePrimeLight, fontWeight = FontWeight.Bold)
                            TimeTickerItem(value = timeBreakdown.seconds, label = "SEC")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Prime lore or Next Prime countdown
                        if (isPrime) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = GoldAccent.copy(alpha = 0.15f),
                                border = BorderStroke(1.dp, GoldBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = PrimeCalculator.getPrimeLore(daysCount),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            lineHeight = 18.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = RomanticWhite
                                    )
                                }
                            }
                        } else {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Next Prime Day: #$nextPrime",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextMuted
                                    )
                                    Text(
                                        text = "in $daysUntilNext ${if (daysUntilNext == 1L) "day" else "days"}",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = GoldAccent
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                val progress = if (nextPrime > prevPrime) {
                                    ((daysCount - prevPrime).toFloat() / (nextPrime - prevPrime).toFloat()).coerceIn(0f, 1f)
                                } else 0.5f
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = RosePrimeLight,
                                    trackColor = VelvetSurfaceVariant,
                                    strokeCap = StrokeCap.Round
                                )
                            }
                        }
                    }
                }
            }
        }

        // GM Daily Romance Quote Card
        item {
            RomanticCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("daily_quote_card"),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Good Morning",
                            tint = GoldAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GM • DAILY ROMANCE AFFIRMATION",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = GoldAccent
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dailyQuote,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic,
                            lineHeight = 22.sp
                        ),
                        color = RomanticWhite
                    )
                }
            }
        }

        // Quick Intimacy & Romance Shortcuts Grid
        item {
            SectionHeader(title = "PRIME TIME RITUALS", subtitle = "Deepen intimacy and romance today")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShortcutCard(
                    title = "Daily Sync",
                    subtitle = "5-Min Check-In",
                    icon = Icons.Default.Forum,
                    iconTint = RosePrimeLight,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("shortcut_check_in"),
                    onClick = { onNavigateToTab(1) }
                )
                ShortcutCard(
                    title = "Date Night",
                    subtitle = "Spin Roulette",
                    icon = Icons.Default.Casino,
                    iconTint = GoldAccent,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("shortcut_date_night"),
                    onClick = { onNavigateToTab(2) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShortcutCard(
                    title = "Vouchers",
                    subtitle = "${coupons.size - redeemedCouponsCount} Available",
                    icon = Icons.Default.Loyalty,
                    iconTint = AmethystPrime,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("shortcut_coupons"),
                    onClick = { onNavigateToTab(3) }
                )
                ShortcutCard(
                    title = "Vault",
                    subtitle = "${memories.size} Memories",
                    icon = Icons.Default.PhotoAlbum,
                    iconTint = MintSuccess,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("shortcut_vault"),
                    onClick = { onNavigateToTab(4) }
                )
            }
        }

        // Couple Profile Love Languages & Status Banner
        item {
            RomanticCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LOVE LANGUAGES",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = TextMuted
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = HeartRed,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LoveLanguageBadge(
                            partnerName = profile.partner1Name,
                            language = profile.partner1LoveLanguage,
                            modifier = Modifier.weight(1f)
                        )
                        LoveLanguageBadge(
                            partnerName = profile.partner2Name,
                            language = profile.partner2LoveLanguage,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (profile.customStatusMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = VelvetSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "💬 \"${profile.customStatusMessage}\"",
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                color = RomanticWhite,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeTickerItem(value: Long, label: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = String.format("%02d", value),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            ),
            color = RomanticWhite
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            ),
            color = TextMuted,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}

@Composable
fun ShortcutCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    RomanticCard(
        modifier = modifier.clickable { onClick() },
        backgroundColor = VelvetCard
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = RomanticWhite
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
fun LoveLanguageBadge(
    partnerName: String,
    language: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = VelvetSurfaceVariant,
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = partnerName,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = RosePrimeLight
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = language,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = RomanticWhite
            )
        }
    }
}
