package com.example.productapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.productapp.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class  AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _authState = MutableStateFlow(authRepository.currentUser != null)
    val authState: StateFlow<Boolean> = _authState

    private val _isAdmin = MutableStateFlow(authRepository.isAdmin)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow<String?>(null)
    val success: StateFlow<String?> = _success

    fun login(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            _error.value = "Email hoặc mật khẩu không được để trống"
            return
        }
        _success.value = null // Xóa thông báo thành công cũ
        authRepository.login(email, pass) { success, msg ->
            if (success) {
                _isAdmin.value = authRepository.isAdmin
                _authState.value = true
                _error.value = null
            } else {
                _error.value = msg ?: "Đăng nhập thất bại"
            }
        }
    }

    fun register(email: String, pass: String, confirmPassword: String) {
        if (email.isEmpty() || pass.isEmpty() || confirmPassword.isEmpty()) {
            _error.value = "Email hoặc mật khẩu không được để trống"
            return
        }
        if (pass.length < 6) {
            _error.value = "Mật khẩu phải có ít nhất 6 ký tự"
            return
        }
        if (pass != confirmPassword) {
            _error.value = "Mật khẩu không khớp"
            return
        }
        
        _success.value = null // Xóa thông báo thành công cũ

        authRepository.register(email, pass, confirmPassword) { success, msg ->
            if (success) {
                authRepository.logout() // Đăng xuất session vừa tạo để không auto login
                _isAdmin.value = false
                _success.value = "Đăng ký thành công!"
                _error.value = null
            } else {
                _error.value = msg ?: "Đăng ký thất bại"
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _success.value = null
        _authState.value = false
        _isAdmin.value = false
    }
}
