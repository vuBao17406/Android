package com.example.productapp.data

import android.util.Log
import com.example.productapp.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getProducts(onResult: (List<Product>) -> Unit, onError: (String) -> Unit = {}) {
        db.collection("products")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("FIREBASE", "Error: ${error.message}")
                    onError(error.message ?: "Lỗi không xác định")
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                if (value == null) {
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                val list = value.documents.mapNotNull { doc ->
                    try {
                        doc.toObject(Product::class.java)?.also { it.id = doc.id }
                    } catch (e: Exception) {
                        Log.e("FIREBASE", "Parse error: ${e.message}")
                        null
                    }
                }
                onResult(list)
            }
    }

    fun addProduct(product: Product, onResult: (Boolean) -> Unit) {
        db.collection("products").add(product)
            .addOnCompleteListener { onResult(it.isSuccessful) }
    }

    fun updateProduct(product: Product, onResult: (Boolean) -> Unit) {
        val updates = hashMapOf<String, Any>(
            "name" to product.name,
            "category" to product.category,
            "price" to product.price,
            "imageUrl" to product.imageUrl
        )
        db.collection("products").document(product.id).update(updates)
            .addOnCompleteListener { onResult(it.isSuccessful) }
    }

    fun deleteProduct(productId: String, onResult: (Boolean) -> Unit) {
        db.collection("products").document(productId).delete()
            .addOnCompleteListener { onResult(it.isSuccessful) }
    }
}