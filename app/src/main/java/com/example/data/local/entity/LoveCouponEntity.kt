package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "love_coupons")
data class LoveCouponEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String, // "Pampering", "Food", "Spontaneous", "Romance", "Playful"
    val isRedeemed: Boolean = false,
    val redeemedDate: String? = null,
    val isCustom: Boolean = false
)
