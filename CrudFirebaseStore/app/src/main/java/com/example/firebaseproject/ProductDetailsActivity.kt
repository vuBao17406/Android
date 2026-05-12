package com.example.firebaseproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseproject.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

class ProductDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseprojectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryDark
                ) {
                    val courseList = remember { mutableStateListOf<Product?>() }
                    val isLoading = remember { mutableStateOf(true) }
                    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

                    DisposableEffect(Unit) {
                        val listener = db.collection("Courses")
                            .addSnapshotListener { queryDocumentSnapshots, error ->
                                if (error != null) {
                                    Toast.makeText(
                                        this@ProductDetailsActivity,
                                        "Lỗi khi tải dữ liệu",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isLoading.value = false
                                    return@addSnapshotListener
                                }

                                if (queryDocumentSnapshots != null) {
                                    courseList.clear()
                                    if (!queryDocumentSnapshots.isEmpty) {
                                        for (d in queryDocumentSnapshots.documents) {
                                            val c: Product? = d.toObject(Product::class.java)
                                            c?.courseID = d.id
                                            courseList.add(c)
                                        }
                                    }
                                }
                                isLoading.value = false
                            }

                        onDispose {
                            listener.remove()
                        }
                    }

                    CourseListScreen(
                        context = LocalContext.current,
                        courseList = courseList,
                        isLoading = isLoading.value
                    )
                }
            }
        }
    }
}


@Composable
fun CourseListScreen(
    context: Context,
    courseList: SnapshotStateList<Product?>,
    isLoading: Boolean
) {
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
            modifier = Modifier.fillMaxSize()
        ) {
            // Custom Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(GradientStart, GradientEnd)
                        )
                    )
                    .padding(top = 44.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                (context as? ComponentActivity)?.finish()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = "Danh Sách Sản Phẩm",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        // Course count badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${courseList.size}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Nhấn vào sản phẩm để chỉnh sửa",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }

            if (isLoading) {
                // Loading state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = AccentLight,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Đang tải sản phẩm...",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = TextSecondary
                            )
                        )
                    }
                }
            } else if (courseList.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📚",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Chưa có sản phẩm nào",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        )
                        Text(
                            text = "Hãy thêm sản phẩm mới!",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = TextSecondary
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            } else {
                // Course list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    itemsIndexed(
                        items = courseList,
                        key = { _, item -> item?.courseID ?: item.hashCode() }
                    ) { index, item ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(tween(300, delayMillis = index * 80)) +
                                    slideInVertically(tween(300, delayMillis = index * 80)) { it / 2 }
                        ) {
                            CourseCard(
                                course = item,
                                index = index,
                                context = context,
                                onDelete = {
                                    courseList.remove(item)
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(
    course: Product?,
    index: Int,
    context: Context,
    onDelete: () -> Unit
) {
    val accentColors = listOf(CardAccent1, CardAccent2, CardAccent3, GradientStart, AccentLight)
    val currentAccent = accentColors[index % accentColors.size]

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceCard
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            val i = Intent(context, UpdateProduct::class.java)
            i.putExtra("courseName", course?.courseName)
            i.putExtra("courseDuration", course?.courseDuration)
            i.putExtra("courseDescription", course?.courseDescription)
            i.putExtra("courseID", course?.courseID)
            context.startActivity(i)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Accent top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(currentAccent, currentAccent.copy(alpha = 0.4f))
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Index badge
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(currentAccent, currentAccent.copy(alpha = 0.6f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Course info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    course?.courseName?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    course?.courseDuration?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(currentAccent)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Giá: $it",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    color = TextSecondary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    course?.courseDescription?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = TextHint
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            // Action buttons row
            Divider(
                color = TextHint.copy(alpha = 0.15f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Edit button
                TextButton(
                    onClick = {
                        val i = Intent(context, UpdateProduct::class.java)
                        i.putExtra("courseName", course?.courseName)
                        i.putExtra("courseDuration", course?.courseDuration)
                        i.putExtra("courseDescription", course?.courseDescription)
                        i.putExtra("courseID", course?.courseID)
                        context.startActivity(i)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = AccentLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sửa",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccentLight
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Delete button
                TextButton(
                    onClick = {
                        deleteDataFromFirebase(course?.courseID, context) {
                            onDelete()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = ErrorColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Xóa",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = ErrorColor
                        )
                    )
                }
            }
        }
    }
}


private fun deleteDataFromFirebase(courseID: String?, context: Context, onSuccess: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("Courses").document(courseID.toString()).delete().addOnSuccessListener {
        Toast.makeText(context, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show()
        onSuccess()
    }.addOnFailureListener {
        Toast.makeText(context, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show()
    }
}
