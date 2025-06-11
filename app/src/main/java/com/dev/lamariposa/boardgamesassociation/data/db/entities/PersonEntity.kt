package com.dev.lamariposa.boardgamesassociation.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String?,
    val phone: String?,
    val address: String?
)
