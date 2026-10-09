package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.LoveCouponEntity
import com.example.ui.components.CategoryChip
import com.example.ui.components.RomanticCard
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintSuccess
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel

@Composable
fun CouponsScreen(
    viewModel: LovePrimeViewModel,
    modifier: Modifier = Modifier
) {
    val coupons by viewModel.coupons.collectAsState()
    var statusFilter by remember { mutableStateOf("All") }
    var categoryFilter by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val usedCount = coupons.count { it.isRedeemed }
    val readyCount = coupons.count { !it.isRedeemed }
    val totalCount = coupons.size
    val usedPercentage = if (totalCount > 0) (usedCount.toFloat() / totalCount.toFloat()) else 0f

    val statusFilters = listOf("All", "Ready to Use ($readyCount)", "Used ($usedCount)")
    val categoryFilters = listOf("All", "Pampering", "Food", "Romance", "Cozy", "Adventure", "Spontaneous")

    val filteredCoupons = coupons.filter { coupon ->
        val matchesStatus = when (statusFilter) {
            "All" -> true
            statusFilters[1] -> !coupon.isRedeemed
            statusFilters[2] -> coupon.isRedeemed
            else -> true
        }
        val matchesCategory = if (categoryFilter == "All") true else coupon.category.equals(categoryFilter, ignoreCase = true)
        matchesStatus && matchesCategory
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("coupons_screen"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with Tracker Summary
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LOVE COUPONS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = RomanticWhite
                        )
                        Text(
                            text = "Redeemable romantic rewards with status tracking",
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
                            text = "$readyCount Available",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = RosePrimeLight
                        )
                    }
                }
            }

            // Status Progress & Overview Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("coupons_progress_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetCard),
                    border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(GoldAccent.copy(alpha = 0.6f), RosePrimeLight)))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Loyalty,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "REWARD USAGE TRACKER",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    ),
                                    color = GoldAccent
                                )
                            }
                            Text(
                                text = "${(usedPercentage * 100).toInt()}% Redeemed",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = RomanticWhite
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { usedPercentage },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MintSuccess,
                            trackColor = VelvetSurfaceVariant,
                            strokeCap = StrokeCap.Round
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "✨ $readyCount Ready to Redeem",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = RosePrimeLight
                            )
                            Text(
                                text = "✓ $usedCount Used so far",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = MintSuccess
                            )
                        }
                    }
                }
            }

            // Status Filter Tabs
            item {
                Text(
                    text = "FILTER BY STATUS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    items(statusFilters) { filter ->
                        CategoryChip(
                            text = filter,
                            isSelected = statusFilter == filter,
                            onClick = { statusFilter = filter },
                            modifier = Modifier.testTag("status_filter_${filter.take(4)}")
                        )
                    }
                }
            }

            // Category Filters
            item {
                Text(
                    text = "CATEGORY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    items(categoryFilters) { cat ->
                        CategoryChip(
                            text = cat,
                            isSelected = categoryFilter == cat,
                            onClick = { categoryFilter = cat },
                            modifier = Modifier.testTag("category_filter_$cat")
                        )
                    }
                }
            }

            // Coupon Cards with Direct Status Toggle
            if (filteredCoupons.isEmpty()) {
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
                                    imageVector = Icons.Default.Loyalty,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "No coupons match the current filter",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextMuted
                                )
                                Text(
                                    text = "Tap '+' to create a custom reward or adjust your filters above.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            } else {
                items(filteredCoupons, key = { it.id }) { coupon ->
                    LoveCouponToggleCard(
                        coupon = coupon,
                        onStatusToggle = { isUsed ->
                            if (isUsed) {
                                viewModel.redeemCoupon(coupon)
                            } else {
                                viewModel.resetCoupon(coupon)
                            }
                        },
                        onDelete = { viewModel.deleteCoupon(coupon) }
                    )
                }
            }
        }

        // FAB to Add Custom Coupon
        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 96.dp)
                .testTag("add_coupon_fab"),
            containerColor = RosePrime,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Create Custom Love Coupon")
        }
    }

    // Create Custom Coupon Dialog
    if (showCreateDialog) {
        CreateCouponDialog(
            onDismiss = { showCreateDialog = false },
            onAdd = { title, desc, cat ->
                viewModel.addCustomCoupon(title, desc, cat)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun LoveCouponToggleCard(
    coupon: LoveCouponEntity,
    onStatusToggle: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val cardBorder = if (coupon.isRedeemed) {
        BorderStroke(1.dp, CardBorder)
    } else {
        BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(GoldAccent.copy(alpha = 0.8f), RosePrimeLight)))
    }

    val icon = getCategoryIcon(coupon.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("coupon_card_${coupon.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (coupon.isRedeemed) VelvetSurfaceVariant.copy(alpha = 0.6f) else VelvetCard
        ),
        border = cardBorder
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Top Row: Category + Custom Tag + Delete Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                if (coupon.isRedeemed) VelvetSurfaceVariant
                                else RosePrime.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = coupon.category,
                            tint = if (coupon.isRedeemed) TextMuted else RosePrimeLight,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CategoryChip(text = coupon.category)

                    if (coupon.isCustom) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = VelvetSurfaceVariant
                        ) {
                            Text(
                                text = "CUSTOM",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = GoldAccent,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                if (coupon.isCustom) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Coupon",
                            tint = TextMuted.copy(alpha = 0.6f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title & Description
            Text(
                text = coupon.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.2.sp
                ),
                color = if (coupon.isRedeemed) RomanticWhite.copy(alpha = 0.7f) else RomanticWhite
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = coupon.description,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Perforated Divider line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(CardBorder)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Status Control: Explicit Switch / Toggle Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Badge & Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (coupon.isRedeemed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (coupon.isRedeemed) MintSuccess else RosePrimeLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = if (coupon.isRedeemed) "USED / REDEEMED" else "READY TO USE",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                            color = if (coupon.isRedeemed) MintSuccess else RosePrimeLight
                        )
                        if (coupon.isRedeemed && coupon.redeemedDate != null) {
                            Text(
                                text = "Claimed on ${coupon.redeemedDate}",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = TextMuted
                            )
                        }
                    }
                }

                // Interactive Status Switch Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("coupon_switch_row_${coupon.id}")
                ) {
                    Text(
                        text = if (coupon.isRedeemed) "Mark Unused" else "Mark Used",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = coupon.isRedeemed,
                        onCheckedChange = { isChecked -> onStatusToggle(isChecked) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MintSuccess,
                            uncheckedThumbColor = RosePrimeLight,
                            uncheckedTrackColor = VelvetSurfaceVariant
                        ),
                        modifier = Modifier.testTag("coupon_toggle_${coupon.id}")
                    )
                }
            }
        }
    }
}

@Composable
fun CreateCouponDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, desc: String, category: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Pampering") }

    val categories = listOf("Pampering", "Food", "Romance", "Cozy", "Adventure", "Spontaneous")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create a Custom Love Voucher", color = RomanticWhite) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Voucher Promise") },
                    placeholder = { Text("e.g., Sunday Head Scratch & Tea") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Terms of Affection") },
                    placeholder = { Text("Describe the sweet details of this coupon...") },
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
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onAdd(title, description, category) },
                colors = ButtonDefaults.buttonColors(containerColor = RosePrime),
                enabled = title.isNotBlank()
            ) {
                Text("Create Voucher")
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

fun getCategoryIcon(category: String): ImageVector {
    return when {
        category.contains("Pampering", ignoreCase = true) -> Icons.Default.Spa
        category.contains("Food", ignoreCase = true) -> Icons.Default.Restaurant
        category.contains("Romance", ignoreCase = true) -> Icons.Default.Favorite
        category.contains("Cozy", ignoreCase = true) -> Icons.Default.Weekend
        category.contains("Adventure", ignoreCase = true) -> Icons.Default.Explore
        category.contains("Spontaneous", ignoreCase = true) -> Icons.Default.Bolt
        else -> Icons.Default.Loyalty
    }
}
