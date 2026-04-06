package com.example.signin_signout.navigation

open class Screen(val rout: String) {
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Home: Screen("home")

}