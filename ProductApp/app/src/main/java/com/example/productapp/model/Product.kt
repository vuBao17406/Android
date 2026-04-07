package com.example.productapp.model

import com.google.firebase.firestore.Exclude

data class  Product(
    @get:Exclude @set:Exclude var id: String = "",
    var name: String = "",
    var category: String = "",
    var price: Double = 0.0,
    var imageUrl: String = ""
)