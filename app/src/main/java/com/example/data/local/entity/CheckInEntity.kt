package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_check_ins")
data class CheckInEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String, // YYYY-MM-DD
    val category: String, // "Deep Connection", "Gratitude", "Pillow Talk", "Fun & Spicy", "Future"
    val question: String,
    val partner1Answer: String = "",
    val partner2Answer: String = "",
    val completedAtMillis: Long = System.currentTimeMillis()
)
