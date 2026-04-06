package com.example.signin_signout.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.example.signin_signout.navigation.Screen

@Composable
fun SignUp(navController: NavController) {

    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Đăng ký",
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

        Spacer(modifier = Modifier.height(10.dp))

        // 👉 Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 👉 Button Đăng ký
        Button(
            onClick = {

                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                    if (password == confirmPassword) {

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {

                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Đăng ký thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate(Screen.SignIn.rout)
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
                            "Mật khẩu không khớp",
                            Toast.LENGTH_SHORT
                        ).show()
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
            Text("Đăng ký")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 👉 Quay lại đăng nhập
        TextButton(
            onClick = {
                navController.navigate(Screen.SignIn.rout)
            }
        ) {
            Text("Đã có tài khoản? Đăng nhập")
        }
    }
}