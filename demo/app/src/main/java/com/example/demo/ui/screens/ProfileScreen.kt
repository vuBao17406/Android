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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBg)
            .verticalScroll(scrollState)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardWhite)
                .padding(16.dp)
        ) {
            Text(
                text = "Hồ sơ",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BluePrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Bác sĩ Nguyễn Văn A", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "Nội khoa • Bệnh viện Đa khoa Trung Ương", fontSize = 12.sp, color = Color.White.copy(alpha = 0.85f))
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    ProfileStat("152", "Bệnh nhân", Color.White)
                    Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.White.copy(alpha = 0.3f)))
                    ProfileStat("4.9", "Đánh giá", Color.White)
                    Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.White.copy(alpha = 0.3f)))
                    ProfileStat("8", "Năm KN", Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Info section
        ProfileSection(title = "Thông tin cá nhân") {
            ProfileInfoRow(Icons.Filled.Badge, "Mã bác sĩ", "BS00123")
            ProfileInfoRow(Icons.Filled.Phone, "Điện thoại", "0912 345 678")
            ProfileInfoRow(Icons.Filled.Email, "Email", "nguyenvana@hospital.vn")
            ProfileInfoRow(Icons.Filled.LocationOn, "Địa chỉ", "123 Lê Lợi, Quận 1, TP.HCM")
        }

        Spacer(modifier = Modifier.height(12.dp))

        ProfileSection(title = "Chuyên môn") {
            ProfileInfoRow(Icons.Filled.MedicalServices, "Chuyên khoa", "Nội khoa")
            ProfileInfoRow(Icons.Filled.School, "Bằng cấp", "Tiến sĩ Y khoa")
            ProfileInfoRow(Icons.Filled.WorkHistory, "Kinh nghiệm", "8 năm")
            ProfileInfoRow(Icons.Filled.LocalHospital, "Bệnh viện", "BV Đa khoa Trung Ương")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Settings section
        ProfileSection(title = "Cài đặt") {
            ProfileActionRow(Icons.Filled.Notifications, "Thông báo", showToggle = true)
            ProfileActionRow(Icons.Filled.Lock, "Đổi mật khẩu")
            ProfileActionRow(Icons.Filled.Language, "Ngôn ngữ", value = "Tiếng Việt")
            ProfileActionRow(Icons.Filled.Help, "Trợ giúp & Hỗ trợ")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Logout button
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEE2E2)),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Icon(Icons.Filled.Logout, contentDescription = null, tint = RedDanger, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Đăng xuất", color = RedDanger, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ProfileStat(value: String, label: String, textColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
        Text(text = label, fontSize = 11.sp, color = textColor.copy(alpha = 0.8f))
    }
}

@Composable
fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GrayText)
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun ProfileInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BlueLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = GrayText)
            Text(text = value, fontSize = 13.sp, color = DarkText, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ProfileActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String = "",
    showToggle: Boolean = false
) {
    var toggled by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BlueLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, fontSize = 13.sp, color = DarkText, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        if (showToggle) {
            Switch(
                checked = toggled,
                onCheckedChange = { toggled = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = BluePrimary)
            )
        } else if (value.isNotEmpty()) {
            Text(text = value, fontSize = 12.sp, color = GrayText)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = GrayText, modifier = Modifier.size(18.dp))
        } else {
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = GrayText, modifier = Modifier.size(18.dp))
        }
    }
}
