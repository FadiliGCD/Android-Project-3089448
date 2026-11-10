package com.fadili.autocare.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vehicles",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val vehicleId: Long = 0,

    val userId: Long = 1L,

    val make: String,

    val model: String,

    val year: Int,

    val registration: String,

    val mileage: Int,

    val createdAt: Long = System.currentTimeMillis()
)
