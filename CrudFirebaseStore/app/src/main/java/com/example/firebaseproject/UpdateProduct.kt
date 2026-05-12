package com.example.firebaseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.firebaseproject.ui.theme.*
import android.content.Context
import android.content.Intent

import android.text.TextUtils
import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.firebase.firestore.FirebaseFirestore

class UpdateProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseprojectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryDark
                ) {
                    firebaseUI(
                        LocalContext.current,
                        intent.getStringExtra("courseName"),
                        intent.getStringExtra("courseDuration"),
                        intent.getStringExtra("courseDescription"),
                        intent.getStringExtra("courseID")
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun firebaseUI(
        context: Context,
        name: String?,
        duration: String?,
        description: String?,
        courseID: String?
    ) {
        val courseName = remember { mutableStateOf(name ?: "") }
        val courseDuration = remember { mutableStateOf(duration ?: "") }
        val courseDescription = remember { mutableStateOf(description ?: "") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(PrimaryDark, PrimaryMedium, SurfaceDark)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            (context as? ComponentActivity)?.finish()
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceCardLight.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                // Header Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(GradientStart, GradientEnd)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update Product",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Cập Nhật Sản Phẩm",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Chỉnh sửa thông tin sản phẩm",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = TextSecondary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceCard
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        StyledTextField(
                            value = courseName.value,
                            onValueChange = { courseName.value = it },
                            label = "Tên sản phẩm",
                            placeholder = "Nhập tên sản phẩm"
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        StyledTextField(
                            value = courseDuration.value,
                            onValueChange = { courseDuration.value = it },
                            label = "Giá sản phẩm",
                            placeholder = "Nhập giá sản phẩm"
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        StyledTextField(
                            value = courseDescription.value,
                            onValueChange = { courseDescription.value = it },
                            label = "Mô tả",
                            placeholder = "Nhập mô tả sản phẩm",
                            singleLine = false,
                            minHeight = 100.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        if (TextUtils.isEmpty(courseName.value.toString())) {
                            Toast.makeText(context, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show()
                        } else if (TextUtils.isEmpty(courseDuration.value.toString())) {
                            Toast.makeText(context, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show()
                        } else if (TextUtils.isEmpty(courseDescription.value.toString())) {
                            Toast.makeText(context, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show()
                        } else {
                            updateDataToFirebase(
                                courseID,
                                courseName.value,
                                courseDuration.value,
                                courseDescription.value,
                                context
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(GradientStart, GradientEnd)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Lưu Thay Đổi",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    private fun updateDataToFirebase(
        courseID: String?,
        name: String?,
        duration: String?,
        description: String?,
        context: Context
    ) {
        val updatedCourse = Product(courseID, name, duration, description)
        val db = FirebaseFirestore.getInstance()
        db.collection("Courses").document(courseID.toString()).set(updatedCourse)
            .addOnSuccessListener {
                Toast.makeText(context, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show()
                if (context is ComponentActivity) {
                    context.finish()
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Cập nhật thất bại: " + it.message, Toast.LENGTH_SHORT).show()
            }
    }
}
