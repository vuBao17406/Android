package com.example.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleScreen() {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Lịch khám, 1 = Lịch trực

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Lịch trình",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkText
            )
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GrayBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.FilterList, contentDescription = "Lọc", tint = DarkText, modifier = Modifier.size(20.dp))
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TabItem(
                text = "Lịch khám",
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                text = "Lịch trực",
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f)
            )
        }

        if (selectedTab == 0) {
            AppointmentListContent()
        } else {
            DutyScheduleContent()
        }
    }
}

@Composable
fun TabItem(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(bottom = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) BluePrimary else GrayText,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.5.dp)
                .background(if (selected) BluePrimary else Color.Transparent)
        )
    }
}

@Composable
fun AppointmentListContent() {
    val scrollState = rememberScrollState()
    val days = listOf("T2\n6", "T3\n7", "T4\n8", "T5\n9", "T6\n10", "T7\n11", "CN\n12")
    var selectedDay by remember { mutableStateOf(2) } // T4 = index 2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBg)
    ) {
        // Month header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tháng 4/2026", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkText)
            Text(text = "8 lịch hẹn", fontSize = 13.sp, color = GrayText)
        }

        // Days row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEachIndexed { index, day ->
                val parts = day.split("\n")
                val dayName = parts[0]
                val dayNum = parts[1]
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selectedDay == index) BluePrimary else Color.Transparent)
                        .clickable { selectedDay = index }
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dayName,
                        fontSize = 11.sp,
                        color = if (selectedDay == index) Color.White else GrayText
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = dayNum,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedDay == index) Color.White else DarkText
                    )
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE2E8F0))

        // Appointment list
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppointmentCard(
                name = "Trần Thị Minh Châu",
                age = "35 tuổi · Nội khoa",
                time = "08:00",
                status = "Đã duyệt",
                statusColor = GreenSuccess,
                statusBg = Color(0xFFDCFCE7),
                symptoms = "Đau đầu, sốt nhẹ 38.5°C",
                borderColor = GreenSuccess,
                showActions = false
            )
            AppointmentCard(
                name = "Lê Quốc Hùng",
                age = "52 tuổi · Tim mạch",
                time = "09:15",
                status = "Chờ duyệt",
                statusColor = OrangeWarning,
                statusBg = Color(0xFFFFFBEB),
                symptoms = "Khó thở, tức ngực, mệt mỏi",
                borderColor = OrangeWarning,
                showActions = true
            )
            AppointmentCard(
                name = "Phạm Ngọc Ánh",
                age = "28 tuổi · Tiêu hóa",
                time = "10:30",
                status = "Chờ duyệt",
                statusColor = OrangeWarning,
                statusBg = Color(0xFFFFFBEB),
                symptoms = "Đau bụng, buồn nôn, tiêu chảy",
                borderColor = OrangeWarning,
                showActions = true
            )
            AppointmentCard(
                name = "Nguyễn Thanh Tùng",
                age = "44 tuổi · Nội khoa",
                time = "11:00",
                status = "Đã duyệt",
                statusColor = GreenSuccess,
                statusBg = Color(0xFFDCFCE7),
                symptoms = "Kiểm tra định kỳ huyết áp",
                borderColor = GreenSuccess,
                showActions = false
            )
        }
    }
}

@Composable
fun AppointmentCard(
    name: String,
    age: String,
    time: String,
    status: String,
    statusColor: Color,
    statusBg: Color,
    symptoms: String,
    borderColor: Color,
    showActions: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCBD5E1)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    Text(text = age, fontSize = 12.sp, color = GrayText)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = time, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusBg)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = status, fontSize = 10.sp, color = statusColor, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = GrayText, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = symptoms, fontSize = 12.sp, color = GrayText)
            }
            if (showActions) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Approve button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFDCFCE7))
                            .clickable { }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Đồng ý khám", fontSize = 12.sp, color = GreenSuccess, fontWeight = FontWeight.SemiBold)
                    }
                    // Reject button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFEE2E2))
                            .clickable { }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Cancel, contentDescription = null, tint = RedDanger, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Từ chối", fontSize = 12.sp, color = RedDanger, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun DutyScheduleContent() {
    val scrollState = rememberScrollState()
    val days = listOf("T2\n6", "T3\n7", "T4\n8", "T5\n9", "T6\n10", "T7\n11", "CN\n12")
    var selectedDay by remember { mutableStateOf(2) } // T4

    data class DutyShift(val day: String, val shift: String, val time: String, val dept: String, val color: Color, val bgColor: Color)

    val shifts = listOf(
        DutyShift("T2", "Ca sáng", "07:00 - 12:00", "Nội khoa", BluePrimary, Color(0xFFEFF6FF)),
        DutyShift("T3", "Ca chiều", "13:00 - 17:30", "Nội khoa", GreenSuccess, Color(0xFFDCFCE7)),
        DutyShift("T4", "Ca trực đêm", "20:00 - 07:00", "Cấp cứu", Color(0xFF8B5CF6), Color(0xFFF5F3FF)),
        DutyShift("T5", "Ca sáng", "07:00 - 12:00", "Nội khoa", BluePrimary, Color(0xFFEFF6FF)),
        DutyShift("T6", "Ca chiều", "13:00 - 17:30", "Tim mạch", OrangeWarning, Color(0xFFFFFBEB)),
        DutyShift("CN", "Ca trực đêm", "20:00 - 07:00", "Cấp cứu", Color(0xFF8B5CF6), Color(0xFFF5F3FF))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBg)
    ) {
        // Week navigator
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Filled.ChevronLeft, contentDescription = null, tint = DarkText)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "6 – 12 Tháng 4", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Text(text = "Năm 2026", fontSize = 11.sp, color = GrayText)
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = DarkText)
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Days
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.forEachIndexed { index, day ->
                        val parts = day.split("\n")
                        val dayName = parts[0]
                        val dayNum = parts[1]
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedDay == index) BluePrimary else Color.Transparent)
                                .clickable { selectedDay = index }
                                .padding(horizontal = 7.dp, vertical = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = dayName, fontSize = 10.sp, color = if (selectedDay == index) Color.White else GrayText)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = dayNum, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (selectedDay == index) Color.White else DarkText)
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(if (selectedDay == index) Color.White else BluePrimary)
                            )
                        }
                    }
                }
            }
        }

        // Duty list
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            shifts.forEach { shift ->
                DutyShiftCard(shift.day, shift.shift, shift.time, shift.dept, shift.color, shift.bgColor)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Weekly summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Tổng kết tuần này", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        SummaryItem("4", "Ca trực", BluePrimary)
                        SummaryItem("36", "Giờ làm", GreenSuccess)
                        SummaryItem("2", "Ca đêm", Color(0xFF8B5CF6))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DutyShiftCard(day: String, shift: String, time: String, dept: String, color: Color, bgColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = day, fontSize = 12.sp, color = GrayText, fontWeight = FontWeight.Medium, modifier = Modifier.width(26.dp))
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(44.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = shift, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Text(text = "$time · $dept", fontSize = 12.sp, color = GrayText)
            }
        }
    }
}

@Composable
fun SummaryItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Text(text = label, fontSize = 12.sp, color = GrayText)
    }
}
