package com.example.productapp.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.FirebaseRepository
import com.example.productapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProductViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _isLoading.value = true
        repository.getProducts(
            onResult = { list ->
                _products.value = list
                _isLoading.value = false
                // Nếu nhận được data (dù rỗng) thì xoá lỗi cũ
                if (_errorMessage.value?.contains("PERMISSION_DENIED") == false) {
                    _errorMessage.value = null
                }
            },
            onError = { error ->
                _isLoading.value = false
                _errorMessage.value = when {
                    error.contains("PERMISSION_DENIED") ->
                        "⚠️ Lỗi quyền truy cập Firestore!\nVào Firebase Console → Firestore → Rules → đổi thành:\nallow read, write: if request.auth != null;"
                    else -> "Lỗi: $error"
                }
            }
        )
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val maxSize = 400
            val width = originalBitmap.width
            val height = originalBitmap.height
            val scale = minOf(maxSize.toFloat() / width, maxSize.toFloat() / height, 1f)
            val resized = Bitmap.createScaledBitmap(originalBitmap, (width * scale).toInt(), (height * scale).toInt(), true)

            val outputStream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            null
        }
    }

    fun addProduct(name: String, category: String, price: Double, imageBase64: String) {
        _isLoading.value = true
        val product = Product(name = name, category = category, price = price, imageUrl = imageBase64)
        repository.addProduct(product) { success ->
            _isLoading.value = false
            if (!success) {
                _errorMessage.value = "Thêm sản phẩm thất bại. Kiểm tra lại Firestore Rules."
            }
        }
    }

    fun updateProduct(product: Product) {
        _isLoading.value = true
        repository.updateProduct(product) { success ->
            _isLoading.value = false
            if (!success) {
                _errorMessage.value = "Cập nhật thất bại. Kiểm tra lại Firestore Rules."
            }
        }
    }

    fun deleteProduct(productId: String) {
        _isLoading.value = true
        repository.deleteProduct(productId) { success ->
            _isLoading.value = false
            if (!success) {
                _errorMessage.value = "Xoá thất bại. Kiểm tra lại Firestore Rules."
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
