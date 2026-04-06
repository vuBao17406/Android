package com.example.productapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productapp.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel = viewModel(), onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()
    val error by authViewModel.error.collectAsState()

    LaunchedEffect(authState) {
        if (authState) {
            onLoginSuccess()
        }
    }

    Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = if (isRegisterMode) "Đăng Ký" else "Đăng Nhập",
                style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(visible = isRegisterMode) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Nhập lại mật khẩu") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                )
            }
        }

        AnimatedVisibility(visible = error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
            )
        }

        val success by authViewModel.success.collectAsState()
        AnimatedVisibility(visible = success != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = success ?: "",
                    color = androidx.compose.ui.graphics.Color.Red,
                    style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
                onClick = {
                    if (isRegisterMode) {
                        authViewModel.register(email, password, confirmPassword)
                    } else {
                        authViewModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotEmpty() && password.isNotEmpty()
        ) { Text(if (isRegisterMode) "ĐĂNG KÝ" else "ĐĂNG NHẬP") }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
                onClick = {
                    isRegisterMode = !isRegisterMode
                    email = ""
                    password = ""
                    confirmPassword = ""
                }
        ) {
            Text(
                    if (isRegisterMode) "Đã có tài khoản? Đăng nhập"
                    else "Chưa có tài khoản? Đăng ký ngay"
            )
        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen() {
    LoginScreen(authViewModel = viewModel(), onLoginSuccess = {})
}
//
