package com.example.productapp.ui.screen

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productapp.model.Product
import com.example.productapp.ui.viewmodel.AuthViewModel
import com.example.productapp.ui.viewmodel.ProductViewModel

// Composable hiển thị ảnh từ chuỗi Base64
@Composable
fun Base64Image(base64: String, modifier: Modifier = Modifier) {
    val bitmap = remember(base64) {
        try {
            val bytes = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            null
        }
    }
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("🖼️")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productViewModel: ProductViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val products by productViewModel.products.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()
    val isAdmin by authViewModel.isAdmin.collectAsState()
    val errorMessage by productViewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var selectedImageBase64 by remember { mutableStateOf("") }
    var selectedFileName by remember { mutableStateOf("") }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    // Launcher chọn ảnh từ thư viện
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedFileName = uri.lastPathSegment ?: "Đã chọn ảnh"
            val base64 = productViewModel.uriToBase64(context, uri)
            if (base64 != null) {
                selectedImageBase64 = base64
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Dữ liệu sản phẩm")
                        Text(
                            text = if (isAdmin) "👑 Admin" else "👤 User",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isAdmin) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Đăng xuất")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Form nhập liệu — chỉ hiện với Admin
            if (isAdmin) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Loại sản phẩm") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = priceStr,
                    onValueChange = { priceStr = it },
                    label = { Text("Giá") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Nút chọn ảnh
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = { launcher.launch("image/*") }) {
                        Text("Chọn ảnh")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            selectedImageBase64.isNotEmpty() -> "✓ $selectedFileName"
                            editingProduct?.imageUrl?.isNotEmpty() == true -> "Giữ ảnh cũ"
                            else -> "Chưa chọn ảnh"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Preview ảnh đã chọn
                if (selectedImageBase64.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Base64Image(
                        base64 = selectedImageBase64,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val price = priceStr.toDoubleOrNull() ?: 0.0
                        val finalImageBase64 = if (selectedImageBase64.isNotEmpty())
                            selectedImageBase64
                        else
                            editingProduct?.imageUrl ?: ""

                        if (editingProduct == null) {
                            productViewModel.addProduct(name, category, price, finalImageBase64)
                        } else {
                            val updated = editingProduct!!.copy(
                                name = name,
                                category = category,
                                price = price,
                                imageUrl = finalImageBase64
                            )
                            productViewModel.updateProduct(updated)
                            editingProduct = null
                        }
                        name = ""
                        category = ""
                        priceStr = ""
                        selectedImageBase64 = ""
                        selectedFileName = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && name.isNotEmpty()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(if (editingProduct == null) "THÊM SẢN PHẨM" else "CẬP NHẬT SẢN PHẨM")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Danh sách sản phẩm:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Hiển thị lỗi nếu có (thường là Firestore rules chưa đúng)
            if (errorMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = errorMessage ?: "",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (products.isEmpty() && errorMessage == null) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chưa có sản phẩm nào", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            LazyColumn {
                items(products) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Base64Image(
                                base64 = product.imageUrl,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Tên sp: ${product.name}", style = MaterialTheme.typography.bodyMedium)
                                Text("Giá sp: ${product.price.toLong()}", style = MaterialTheme.typography.bodySmall)
                                Text("Loại sp: ${product.category}", style = MaterialTheme.typography.bodySmall)
                            }
                            if (isAdmin) {
                                IconButton(onClick = {
                                    name = product.name
                                    category = product.category
                                    priceStr = product.price.toLong().toString()
                                    selectedImageBase64 = ""
                                    selectedFileName = ""
                                    editingProduct = product
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Sửa",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = {
                                    productViewModel.deleteProduct(product.id)
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Xóa",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
