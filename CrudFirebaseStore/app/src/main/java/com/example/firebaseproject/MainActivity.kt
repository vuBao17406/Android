package com.example.firebaseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.firebaseproject.ui.theme.*

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID


class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseprojectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryDark
                ) {
                    FirebaseUI(LocalContext.current)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseUI(context: Context) {

    val courseID = remember { mutableStateOf("") }
    val courseName = remember { mutableStateOf("") }
    val courseDuration = remember { mutableStateOf("") }
    val courseDescription = remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(60.dp))

            // Header with gradient icon
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
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Course",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Thêm Sản Phẩm",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Nhập thông tin sản phẩm mới",
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
                    // Course Name Field
                    StyledTextField(
                        value = courseName.value,
                        onValueChange = { courseName.value = it },
                        label = "Tên sản phẩm",
                        placeholder = "Nhập tên sản phẩm"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Course Duration Field
                    StyledTextField(
                        value = courseDuration.value,
                        onValueChange = { courseDuration.value = it },
                        label = "Giá sản phẩm",
                        placeholder = "Nhập giá sản phẩm"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Course Description Field
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

            // Add Data Button
            Button(
                onClick = {
                    if (TextUtils.isEmpty(courseName.value.toString())) {
                        Toast.makeText(context, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show()
                    } else if (TextUtils.isEmpty(courseDuration.value.toString())) {
                        Toast.makeText(context, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show()
                    } else if (TextUtils.isEmpty(courseDescription.value.toString())) {
                        Toast.makeText(context, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show()
                    } else {
                        courseID.value = UUID.randomUUID().toString()
                        addDataToFirebase(
                            courseID.value,
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
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Thêm Sản Phẩm",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View Courses Button
            OutlinedButton(
                onClick = {
                    context.startActivity(Intent(context, ProductDetailsActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = GradientStart
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = AccentLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Xem Danh Sách Sản Phẩm",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AccentLight
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    minHeight: androidx.compose.ui.unit.Dp = 56.dp
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) GradientStart else TextHint.copy(alpha = 0.3f),
        animationSpec = tween(300)
    )

    Column {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = if (isFocused) AccentLight else TextSecondary
            ),
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextHint
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .onFocusChanged { isFocused = it.isFocused },
            textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 15.sp
            ),
            singleLine = singleLine,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientStart,
                unfocusedBorderColor = TextHint.copy(alpha = 0.3f),
                focusedContainerColor = SurfaceCardLight.copy(alpha = 0.5f),
                unfocusedContainerColor = SurfaceCardLight.copy(alpha = 0.3f),
                cursorColor = AccentLight
            )
        )
    }
}


fun addDataToFirebase(
    courseID: String, courseName: String, courseDuration: String, courseDescription: String, context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // creating a collection reference for our Firebase Firestore database.
    val dbCourses: CollectionReference = db.collection("Courses")

    // adding our data to our courses object class.
    val courses = Product(courseID, courseName, courseDuration, courseDescription)

    // below method is use to add data to Firebase Firestore
    // after the data addition is successful
    dbCourses.add(courses).addOnSuccessListener {
        // we are displaying a success toast message.
        Toast.makeText(
            context, "Sản phẩm đã được thêm thành công!", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "Thêm sản phẩm thất bại \n$e", Toast.LENGTH_SHORT).show()
    }

}