package com.example.signin_signout.ui.theme


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.example.signin_signout.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    val currentUser = firebaseAuth.currentUser
    val email = currentUser?.email ?: "Không có email"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Trang chủ",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 👉 Hiển thị email người dùng
        Text(
            text = "Xin chào: $email"
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 👉 Button Logout
        Button(
            onClick = {
                firebaseAuth.signOut()

                Toast.makeText(
                    context,
                    "Đã đăng xuất",
                    Toast.LENGTH_SHORT
                ).show()

                navController.navigate(Screen.SignIn.rout) {
                    popUpTo(Screen.Home.rout) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đăng xuất")
        }
    }
}