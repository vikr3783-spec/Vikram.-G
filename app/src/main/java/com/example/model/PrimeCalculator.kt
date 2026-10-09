package com.example.model

object PrimeCalculator {

    fun isPrime(n: Long): Boolean {
        if (n <= 1) return false
        if (n <= 3) return true
        if (n % 2 == 0L || n % 3 == 0L) return false
        var i = 5L
        while (i * i <= n) {
            if (n % i == 0L || n % (i + 2L) == 0L) return false
            i += 6L
        }
        return true
    }

    fun nextPrime(after: Long): Long {
        var candidate = if (after < 2) 2L else after + 1L
        while (!isPrime(candidate)) {
            candidate++
        }
        return candidate
    }

    fun previousPrime(before: Long): Long? {
        if (before <= 2) return null
        var candidate = before - 1L
        while (candidate >= 2) {
            if (isPrime(candidate)) return candidate
            candidate--
        }
        return null
    }

    /**
     * Prime Milestone Lore / meaning for romantic bonding
     */
    fun getPrimeLore(days: Long): String {
        return when {
            days == 2L -> "The First Prime: Two souls coming together into one journey."
            days == 3L -> "The Triangle of Trust: Intimacy, Passion, and Commitment."
            days == 7L -> "One Sacred Week: Complete harmony in each other's presence."
            days == 17L -> "Sweet Seventeen: The spark that continues to burn brightly."
            days == 23L -> "The Prime of Radiance: Laughing and growing side by side."
            days == 31L -> "One Full Month Prime: Solidified bonds, forever undivided."
            days == 73L -> "The Silver Spark Prime: Enduring tenderness and quiet joy."
            days == 101L -> "The Century Prime: Over one hundred days of indivisible love."
            days == 137L -> "The Fine Structure Prime: Perfect mathematical attraction."
            days == 199L -> "Nearly 200 days of unforgettable smiles and shared adventures."
            days == 367L -> "One Year + Prime: Beyond 365 days of unconditional dedication."
            days == 733L -> "Double Year Prime: Two years strong, purely resilient."
            isPrime(days) -> "Day $days is a True Prime Number: Indivisible, irreplaceable, unique to us."
            else -> "Every day with you is a building block to the next prime milestone."
        }
    }
}
