package com.example.woof.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(

    // dùng cho hình ảnh dog icon (hình tròn)
    small = RoundedCornerShape(50.dp),

    // dùng cho Card của danh sách
    medium = RoundedCornerShape(
        bottomStart = 16.dp,
        topEnd = 16.dp
    ),

    large = RoundedCornerShape(0.dp)
)