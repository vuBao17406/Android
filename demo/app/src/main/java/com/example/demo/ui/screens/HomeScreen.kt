package com.example.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val BluePrimary = Color(0xFF2563EB)
val BlueLight = Color(0xFFEFF6FF)
val GreenSuccess = Color(0xFF22C55E)
val OrangeWarning = Color(0xFFF59E0B)
val RedDanger = Color(0xFFEF4444)
val GrayBg = Color(0xFFF8FAFC)
val GrayText = Color(0xFF64748B)
val DarkText = Color(0xFF1E293B)
val CardWhite = Color(0xFFFFFFFF)

@Composable
fun HomeScreen(onNavigateToExamination: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBg)
            .verticalScroll(scrollState)
    ) {
        // Header
        HomeHeader()

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Cards
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.MedicalServices,
                    iconBg = BlueLight,
                    iconColor = BluePrimary,
                    value = "2",
                    label = "Ca trực\nhôm nay",
                    highlighted = false
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.People,
                    iconBg = Color(0xFFFFFBEB),
                    iconColor = OrangeWarning,
                    value = "7",
                    label = "Bệnh nhân\nđang chờ",
                    highlighted = true,
                    highlightColor = OrangeWarning
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Completed today
            CompletedTodayCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Quick actions
            Text(
                text = "Thao tác nhanh",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Spacer(modifier = Modifier.height(12.dp))
            QuickActions(onNavigateToExamination = onNavigateToExamination)

            Spacer(modifier = Modifier.height(20.dp))

            // Upcoming appointments
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lịch hẹn gần nhất",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Text(
                    text = "Xem tất cả",
                    fontSize = 13.sp,
                    color = BluePrimary,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            AppointmentItem(
                time = "08:00",
                timeLabel = "Hôm nay",
                name = "Trần Thị Minh C...",
                info = "35 tuổi · Đau đầu, ...",
                status = "Đã duyệt",
                statusColor = GreenSuccess,
                statusBg = Color(0xFFDCFCE7),
                onClick = onNavigateToExamination
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppointmentItem(
                time = "09:15",
                timeLabel = "Hôm nay",
                name = "Lê Quốc Hùng",
                info = "52 tuổi · Tim mạch",
                status = "Chờ duyệt",
                statusColor = OrangeWarning,
                statusBg = Color(0xFFFFFBEB),
                onClick = onNavigateToExamination
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppointmentItem(
                time = "10:30",
                timeLabel = "Hôm nay",
                name = "Phạm Ngọc Ánh",
                info = "28 tuổi · Tiêu hóa",
                status = "Chờ duyệt",
                statusColor = OrangeWarning,
                statusBg = Color(0xFFFFFBEB),
                onClick = onNavigateToExamination
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardWhite)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCBD5E1)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Chào buổi tối,",
                        fontSize = 12.sp,
                        color = GrayText
                    )
                    Text(
                        text = "Bác sĩ Nguyễn Văn A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(GreenSuccess)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nội khoa",
                            fontSize = 12.sp,
                            color = GrayText
                        )
                    }
                }
            }
            // Notification bell
            Box {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(GrayBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Thông báo",
                        tint = DarkText,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(RedDanger)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "3", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconBg: Color,
    iconColor: Color,
    value: String,
    label: String,
    highlighted: Boolean,
    highlightColor: Color = BluePrimary
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = if (highlighted) androidx.compose.foundation.BorderStroke(2.dp, highlightColor) else null
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = value,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (highlighted) highlightColor else DarkText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = GrayText,
                    lineHeight = 16.sp
                )
            }
            if (highlighted) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(highlightColor)
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 12.dp)
                        .offset(x = (-12).dp, y = 12.dp)
                )
            }
        }
    }
}

@Composable
fun CompletedTodayCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCFCE7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = GreenSuccess,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = "Đã khám hoàn tất hôm nay",
                    fontSize = 13.sp,
                    color = GrayText
                )
                Text(
                    text = "5",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkText
                )
            }
        }
    }
}

data class QuickAction(val icon: ImageVector, val label: String, val iconBg: Color, val iconColor: Color, val badge: Int = 0)

@Composable
fun QuickActions(onNavigateToExamination: () -> Unit) {
    val actions = listOf(
        QuickAction(Icons.Filled.List, "Danh sách\nchờ duyệt", Color(0xFFFFF7ED), OrangeWarning, badge = 3),
        QuickAction(Icons.Filled.CalendarMonth, "Lịch trực\ncủa tôi", BlueLight, BluePrimary),
        QuickAction(Icons.Filled.MedicalServices, "Quản lý\nkhám bệnh", Color(0xFFDCFCE7), GreenSuccess),
        QuickAction(Icons.Filled.PersonOff, "Bệnh nhân\nvắng mặt", Color(0xFFFEE2E2), RedDanger)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        actions.forEach { action ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardWhite)
                    .clickable { if (action.label.contains("khám")) onNavigateToExamination() }
                    .padding(vertical = 12.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(action.iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(action.icon, contentDescription = null, tint = action.iconColor, modifier = Modifier.size(22.dp))
                    }
                    if (action.badge > 0) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(RedDanger)
                                .align(Alignment.TopEnd)
                                .offset(x = 4.dp, y = (-4).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = action.badge.toString(), fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = action.label,
                    fontSize = 10.sp,
                    color = DarkText,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AppointmentItem(
    time: String,
    timeLabel: String,
    name: String,
    info: String,
    status: String,
    statusColor: Color,
    statusBg: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = time, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                Text(text = timeLabel, fontSize = 10.sp, color = GrayText)
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFCBD5E1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkText, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = info, fontSize = 12.sp, color = GrayText, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            // Status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = status, fontSize = 11.sp, color = statusColor, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
