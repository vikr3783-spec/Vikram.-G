package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val note: String,
    val location: String = "",
    val dateMillis: Long,
    val mood: String = "Romantic", // "Romantic", "Joyful", "Silly", "Cozy", "Adventure"
    val daysCountAtMoment: Long = 0,
    val isPrimeMilestone: Boolean = false
)
