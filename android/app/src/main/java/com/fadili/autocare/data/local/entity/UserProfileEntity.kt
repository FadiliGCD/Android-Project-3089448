package com.fadili.autocare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val userId: Long = 1L,

    val fullName: String,

    val email: String? = null,

    val phone: String? = null,

    val createdAt: Long = System.currentTimeMillis()
)
