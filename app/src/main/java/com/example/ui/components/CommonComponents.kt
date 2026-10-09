package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmethystPrime
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HeartRed
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetDark
import com.example.ui.theme.VelvetSurfaceVariant

@Composable
fun RomanticCard(
    modifier: Modifier = Modifier,
    border: BorderStroke? = BorderStroke(1.dp, CardBorder),
    backgroundColor: Color = VelvetCard,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        content()
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (actionText != null && onActionClick != null) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onActionClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = Color.Transparent
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = RosePrimeLight
                )
            }
        }
    }
}

@Composable
fun PrimeBadge(
    days: Long,
    isPrime: Boolean,
    modifier: Modifier = Modifier
) {
    val bgColors = if (isPrime) {
        listOf(GoldAccent, RosePrime)
    } else {
        listOf(AmethystPrime.copy(alpha = 0.5f), VelvetSurfaceVariant)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Brush.horizontalGradient(bgColors))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isPrime) Icons.Default.Star else Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isPrime) VelvetDark else RosePrimeLight
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isPrime) "PRIME DAY #$days" else "DAY #$days",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp
                ),
                color = if (isPrime) VelvetDark else Color.White
            )
        }
    }
}

@Composable
fun HeartRatingBar(
    rating: Int,
    maxRating: Int = 5,
    onRatingChanged: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        for (i in 1..maxRating) {
            val isFilled = i <= rating
            val tint by animateColorAsState(
                targetValue = if (isFilled) HeartRed else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                label = "heartTint"
            )
            Icon(
                imageVector = if (isFilled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Rating $i",
                tint = tint,
                modifier = Modifier
                    .size(26.dp)
                    .then(
                        if (onRatingChanged != null) {
                            Modifier
                                .clip(CircleShape)
                                .clickable { onRatingChanged(i) }
                                .padding(2.dp)
                        } else Modifier
                    )
            )
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val bg = if (isSelected) RosePrime else VelvetSurfaceVariant
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    val border = if (isSelected) null else BorderStroke(1.dp, CardBorder)

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = border
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            color = textColor
        )
    }
}
