package com.example.firebaseproject

import com.google.firebase.firestore.Exclude

data class Product(
    // on below line creating variables.
    @Exclude var courseID: String? = "",
    var courseName: String? = "",
    var courseDuration: String? = "",
    var courseDescription: String? = ""



)
