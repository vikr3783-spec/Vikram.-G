package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_nights")
data class DateNightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String, // "Cozy Home", "Dining", "Outdoor", "Budget", "Playful"
    val costIndicator: String = "$$", // "$", "$$", "$$$"
    val isCompleted: Boolean = false,
    val rating: Int = 0, // 0 to 5 hearts
    val notes: String = "",
    val completedDate: String? = null,
    val isWishlist: Boolean = true
)
