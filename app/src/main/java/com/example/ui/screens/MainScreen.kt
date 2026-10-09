package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Loyalty
import androidx.compose.material.icons.outlined.PhotoAlbum
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SettingsDialog
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.RomanticWhite
import com.example.ui.theme.RosePrime
import com.example.ui.theme.RosePrimeLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VelvetCard
import com.example.ui.theme.VelvetSurfaceVariant
import com.example.ui.viewmodel.LovePrimeViewModel

sealed class LoveNavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    object Moments : LoveNavTab("Prime", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "nav_tab_prime")
    object CheckIn : LoveNavTab("Check-In", Icons.Filled.Forum, Icons.Outlined.Forum, "nav_tab_checkin")
    object Dates : LoveNavTab("Dates", Icons.Filled.Casino, Icons.Outlined.Casino, "nav_tab_dates")
    object Coupons : LoveNavTab("Coupons", Icons.Filled.Loyalty, Icons.Outlined.Loyalty, "nav_tab_coupons")
    object Vault : LoveNavTab("Vault", Icons.Filled.PhotoAlbum, Icons.Outlined.PhotoAlbum, "nav_tab_vault")
}

@Composable
fun MainScreen(
    viewModel: LovePrimeViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    val profile by viewModel.coupleProfile.collectAsState()

    val tabs = listOf(
        LoveNavTab.Moments,
        LoveNavTab.CheckIn,
        LoveNavTab.Dates,
        LoveNavTab.Coupons,
        LoveNavTab.Vault
    )

    // Handle back button to return to first tab if on secondary tabs
    if (selectedTabIndex != 0) {
        BackHandler {
            selectedTabIndex = 0
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            NavigationBar(
                containerColor = VelvetCard,
                contentColor = RomanticWhite,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = selectedTabIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RosePrimeLight,
                            unselectedIconColor = TextMuted,
                            selectedTextColor = RosePrimeLight,
                            unselectedTextColor = TextMuted,
                            indicatorColor = VelvetSurfaceVariant
                        ),
                        modifier = Modifier.testTag(tab.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = selectedTabIndex,
                label = "ScreenTransition"
            ) { tabIndex ->
                when (tabIndex) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToTab = { target -> selectedTabIndex = target },
                        onOpenSettings = { showSettingsDialog = true }
                    )
                    1 -> CheckInScreen(viewModel = viewModel)
                    2 -> DateNightScreen(viewModel = viewModel)
                    3 -> CouponsScreen(viewModel = viewModel)
                    4 -> MemoryVaultScreen(viewModel = viewModel)
                }
            }
        }
    }

    if (showSettingsDialog) {
        SettingsDialog(
            profile = profile,
            onDismiss = { showSettingsDialog = false },
            onSave = { p1, p2, anniversaryMillis, p1Ll, p2Ll, status ->
                viewModel.updateProfile(p1, p2, anniversaryMillis, p1Ll, p2Ll, status)
                showSettingsDialog = false
            }
        )
    }
}
