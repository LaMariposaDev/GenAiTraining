package com.dev.lamariposa.boardgamesassociation.domain.model

data class Person(
    val id: Long = 0,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null
)
