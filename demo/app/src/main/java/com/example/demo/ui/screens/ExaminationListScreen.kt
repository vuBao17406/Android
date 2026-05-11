package com.example.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PatientItem(
    val id: String,
    val name: String,
    val info: String,
    val time: String,
    val status: String,
    val statusColor: Color,
    val statusBg: Color,
    val isExamining: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExaminationListScreen(onNavigateToDetail: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    
    val patients = listOf(
        PatientItem(
            id = "BN002",
            name = "Lê Quốc Hùng",
            info = "52 tuổi · Nam · Tim mạch",
            time = "09:15",
            status = "Đang khám",
            statusColor = BluePrimary,
            statusBg = BlueLight,
            isExamining = true
        ),
        PatientItem(
            id = "BN003",
            name = "Phạm Ngọc Ánh",
            info = "28 tuổi · Nữ · Tiêu hóa",
            time = "10:30",
            status = "Chờ khám",
            statusColor = OrangeWarning,
            statusBg = Color(0xFFFFFBEB)
        ),
        PatientItem(
            id = "BN004",
            name = "Nguyễn Thanh Tùng",
            info = "44 tuổi · Nam · Nội khoa",
            time = "11:00",
            status = "Chờ khám",
            statusColor = OrangeWarning,
            statusBg = Color(0xFFFFFBEB)
        ),
        PatientItem(
            id = "BN001",
            name = "Trần Thị Minh Châu",
            info = "35 tuổi · Nữ · Nội khoa",
            time = "08:00",
            status = "Đã xong",
            statusColor = GreenSuccess,
            statusBg = Color(0xFFDCFCE7)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBg)
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardWhite)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column {
                Text(
                    text = "Quản lý khám bệnh",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hôm nay, 08 Tháng 4",
                    fontSize = 13.sp,
                    color = GrayText
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Tìm kiếm bệnh nhân, mã BS...", fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = GrayText) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedBorderColor = BluePrimary,
                unfocusedContainerColor = CardWhite,
                focusedContainerColor = CardWhite
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        // Summary row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Danh sách chờ khám", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Text(text = "Tổng: ${patients.size}", fontSize = 13.sp, color = GrayText)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Patient list
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(patients) { patient ->
                PatientCard(
                    patient = patient,
                    onClick = onNavigateToDetail
                )
            }
        }
    }
}

@Composable
fun PatientCard(patient: PatientItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFCBD5E1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = patient.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Text(text = patient.id, fontSize = 12.sp, color = BluePrimary, fontWeight = FontWeight.SemiBold)
                        Text(text = patient.info, fontSize = 12.sp, color = GrayText)
                    }
                }
                Icon(Icons.Filled.MoreVert, contentDescription = null, tint = GrayText)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(10.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Hẹn:", fontSize = 13.sp, color = GrayText)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = patient.time, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(patient.statusBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(text = patient.status, fontSize = 12.sp, color = patient.statusColor, fontWeight = FontWeight.SemiBold)
                }
            }
            
            if (patient.isExamining) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Text(text = "Tiếp tục khám", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
