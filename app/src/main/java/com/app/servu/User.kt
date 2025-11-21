package com.app.servu

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val cpf: String? = null,
    val profileImagePath: String? = null
)