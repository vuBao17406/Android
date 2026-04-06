package com.example.productapp.data

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth = FirebaseAuth.getInstance() // lấy đối tượng FirebaseAuth để đăng nhập, đăng ký, ...

    val currentUser get() = auth.currentUser // lấy dữ liệu đang đăng nhập nếu không có trả về null

    val isAdmin: Boolean get() = currentUser?.email == "admin@admin.com" //safecall

    fun login(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun register(email: String, pass: String,confirmPassword: String, onResult: (Boolean, String?) -> Unit) {
        if(pass!=confirmPassword){
            onResult(false,"Mật khẩu không khớp")
            return
        }
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }
}
