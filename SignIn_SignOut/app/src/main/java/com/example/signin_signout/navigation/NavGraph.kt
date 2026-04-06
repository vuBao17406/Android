package com.example.signin_signout.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signin_signout.ui.theme.SignIn
import com.example.signin_signout.ui.theme.SignUp
import com.example.signin_signout.ui.theme.HomeScreen

@Composable
fun Mynavigation() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.rout
    ) {

        // 👉 Màn hình đăng nhập
        composable(Screen.SignIn.rout) {
            SignIn(navController = navController)
        }

        // 👉 Màn hình đăng ký
        composable(Screen.SignUp.rout) {
            SignUp(navController = navController)
        }

        // 👉 Màn hình trang chủ
        composable(Screen.Home.rout) {
            HomeScreen(navController = navController)
        }
    }
}