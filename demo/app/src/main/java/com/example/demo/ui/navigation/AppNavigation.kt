package com.example.demo.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.demo.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Schedule : Screen("schedule")
    object ExaminationList : Screen("examination_list")
    object ExaminationDetail : Screen("examination_detail")
    object Profile : Screen("profile")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Trang chủ", Icons.Filled.Home),
    BottomNavItem(Screen.Schedule, "Lịch trình", Icons.Filled.CalendarMonth),
    BottomNavItem(Screen.ExaminationList, "Khám bệnh", Icons.Filled.MedicalServices),
    BottomNavItem(Screen.Profile, "Hồ sơ", Icons.Filled.Person)
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Screens that show the bottom nav bar
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Schedule.route,
        Screen.ExaminationList.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onItemClick = { screen ->
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) { 
                                        saveState = true 
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(250)) },
            exitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(250)) },
            popEnterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(250)) },
            popExitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(250)) }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToExamination = {
                        navController.navigate(Screen.ExaminationList.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Schedule.route) {
                ScheduleScreen()
            }
            composable(Screen.ExaminationList.route) {
                ExaminationListScreen(
                    onNavigateToDetail = {
                        navController.navigate(Screen.ExaminationDetail.route)
                    }
                )
            }
            composable(Screen.ExaminationDetail.route) {
                ExaminationScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun BottomNavBar(currentRoute: String?, onItemClick: (Screen) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentRoute == item.screen.route
                val isCenter = item.screen == Screen.ExaminationList

                if (isCenter) {
                    // Center special button
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onItemClick(item.screen) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) BluePrimary else Color(0xFFEFF6FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                tint = if (isSelected) Color.White else BluePrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = if (isSelected) BluePrimary else GrayText,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onItemClick(item.screen) }
                            .padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) BluePrimary else GrayText,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = if (isSelected) BluePrimary else GrayText,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(BluePrimary)
                            )
                        }
                    }
                }
            }
        }
    }
}
