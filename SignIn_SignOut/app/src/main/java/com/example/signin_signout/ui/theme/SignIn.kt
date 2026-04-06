package com.example.signin_signout.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.signin_signout.navigation.Screen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignIn(navController: NavController) {

    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Đăng nhập",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 👉 Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 👉 Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 👉 Button Login
        Button(
            onClick = {

                if (email.isNotEmpty() && password.isNotEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate(Screen.Home.rout)
                            } else {
                                Toast.makeText(
                                    context,
                                    it.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                } else {
                    Toast.makeText(
                        context,
                        "Không được để trống!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đăng nhập")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 👉 Chuyển sang SignUp
        TextButton(
            onClick = {
                navController.navigate(Screen.SignUp.rout)
            }
        ) {
            Text("Chưa có tài khoản? Đăng ký")
        }
    }
}