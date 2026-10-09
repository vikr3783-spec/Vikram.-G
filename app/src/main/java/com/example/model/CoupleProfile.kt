package com.example.model

data class CoupleProfile(
    val partner1Name: String = "Taylor",
    val partner2Name: String = "Jordan",
    val anniversaryDateMillis: Long = System.currentTimeMillis() - (101L * 24L * 60L * 60L * 1000L), // Defaults to 101 days (a prime milestone!)
    val partner1LoveLanguage: String = "Words of Affirmation",
    val partner2LoveLanguage: String = "Quality Time",
    val customStatusMessage: String = "Growing stronger each Prime Day ✨"
)
