package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.SeedData
import com.example.data.local.dao.LovePrimeDao
import com.example.data.local.entity.CheckInEntity
import com.example.data.local.entity.DateNightEntity
import com.example.data.local.entity.LoveCouponEntity
import com.example.data.local.entity.MemoryEntity
import com.example.model.CoupleProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class LovePrimeRepository(
    private val dao: LovePrimeDao,
    context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("love_prime_prefs", Context.MODE_PRIVATE)

    private val _coupleProfile = MutableStateFlow(loadProfile())
    val coupleProfile = _coupleProfile.asStateFlow()

    private fun loadProfile(): CoupleProfile {
        val p1 = prefs.getString("p1_name", "Taylor") ?: "Taylor"
        val p2 = prefs.getString("p2_name", "Jordan") ?: "Jordan"
        val anniversary = prefs.getLong(
            "anniversary_millis",
            System.currentTimeMillis() - (101L * 24L * 60L * 60L * 1000L) // Default 101 days
        )
        val l1 = prefs.getString("p1_ll", "Words of Affirmation") ?: "Words of Affirmation"
        val l2 = prefs.getString("p2_ll", "Quality Time") ?: "Quality Time"
        val status = prefs.getString("custom_status", "Growing stronger each Prime Day ✨") ?: "Growing stronger each Prime Day ✨"
        return CoupleProfile(p1, p2, anniversary, l1, l2, status)
    }

    suspend fun updateProfile(profile: CoupleProfile) {
        withContext(Dispatchers.IO) {
            prefs.edit()
                .putString("p1_name", profile.partner1Name)
                .putString("p2_name", profile.partner2Name)
                .putLong("anniversary_millis", profile.anniversaryDateMillis)
                .putString("p1_ll", profile.partner1LoveLanguage)
                .putString("p2_ll", profile.partner2LoveLanguage)
                .putString("custom_status", profile.customStatusMessage)
                .apply()
            _coupleProfile.value = profile
        }
    }

    suspend fun seedDatabaseIfNeeded() {
        withContext(Dispatchers.IO) {
            if (dao.getCouponsCount() == 0) {
                dao.insertAllCoupons(SeedData.getDefaultCoupons())
            }
            if (dao.getDateNightsCount() == 0) {
                dao.insertAllDateNights(SeedData.getDefaultDateNights())
            }
            if (dao.getMemoriesCount() == 0) {
                val profile = _coupleProfile.value
                val defaultMemories = SeedData.getDefaultMemories(System.currentTimeMillis())
                defaultMemories.forEach { dao.insertMemory(it) }
            }
        }
    }

    // Coupons
    val coupons: Flow<List<LoveCouponEntity>> = dao.getAllCoupons()

    suspend fun addCoupon(coupon: LoveCouponEntity): Long = withContext(Dispatchers.IO) {
        dao.insertCoupon(coupon)
    }

    suspend fun updateCoupon(coupon: LoveCouponEntity) = withContext(Dispatchers.IO) {
        dao.updateCoupon(coupon)
    }

    suspend fun deleteCoupon(coupon: LoveCouponEntity) = withContext(Dispatchers.IO) {
        dao.deleteCoupon(coupon)
    }

    // Memories
    val memories: Flow<List<MemoryEntity>> = dao.getAllMemories()

    suspend fun addMemory(memory: MemoryEntity): Long = withContext(Dispatchers.IO) {
        dao.insertMemory(memory)
    }

    suspend fun deleteMemory(memory: MemoryEntity) = withContext(Dispatchers.IO) {
        dao.deleteMemory(memory)
    }

    // Date Nights
    val dateNights: Flow<List<DateNightEntity>> = dao.getAllDateNights()

    suspend fun addDateNight(dateNight: DateNightEntity): Long = withContext(Dispatchers.IO) {
        dao.insertDateNight(dateNight)
    }

    suspend fun updateDateNight(dateNight: DateNightEntity) = withContext(Dispatchers.IO) {
        dao.updateDateNight(dateNight)
    }

    suspend fun deleteDateNight(dateNight: DateNightEntity) = withContext(Dispatchers.IO) {
        dao.deleteDateNight(dateNight)
    }

    // Daily Check Ins
    val checkIns: Flow<List<CheckInEntity>> = dao.getAllCheckIns()

    fun getCheckInByDate(dateString: String): Flow<CheckInEntity?> =
        dao.getCheckInByDate(dateString)

    suspend fun saveCheckIn(checkIn: CheckInEntity) = withContext(Dispatchers.IO) {
        dao.insertCheckIn(checkIn)
    }
}
