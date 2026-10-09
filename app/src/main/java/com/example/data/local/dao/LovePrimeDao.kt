package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.CheckInEntity
import com.example.data.local.entity.DateNightEntity
import com.example.data.local.entity.LoveCouponEntity
import com.example.data.local.entity.MemoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LovePrimeDao {

    // Love Coupons
    @Query("SELECT * FROM love_coupons ORDER BY isRedeemed ASC, id ASC")
    fun getAllCoupons(): Flow<List<LoveCouponEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoupon(coupon: LoveCouponEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoupons(coupons: List<LoveCouponEntity>)

    @Update
    suspend fun updateCoupon(coupon: LoveCouponEntity)

    @Delete
    suspend fun deleteCoupon(coupon: LoveCouponEntity)

    @Query("SELECT COUNT(*) FROM love_coupons")
    suspend fun getCouponsCount(): Int

    // Memories
    @Query("SELECT * FROM memories ORDER BY dateMillis DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity): Long

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)

    @Query("SELECT COUNT(*) FROM memories")
    suspend fun getMemoriesCount(): Int

    // Date Nights
    @Query("SELECT * FROM date_nights ORDER BY isCompleted ASC, id ASC")
    fun getAllDateNights(): Flow<List<DateNightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDateNight(dateNight: DateNightEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDateNights(dateNights: List<DateNightEntity>)

    @Update
    suspend fun updateDateNight(dateNight: DateNightEntity)

    @Delete
    suspend fun deleteDateNight(dateNight: DateNightEntity)

    @Query("SELECT COUNT(*) FROM date_nights")
    suspend fun getDateNightsCount(): Int

    // Daily Check Ins
    @Query("SELECT * FROM daily_check_ins ORDER BY completedAtMillis DESC")
    fun getAllCheckIns(): Flow<List<CheckInEntity>>

    @Query("SELECT * FROM daily_check_ins WHERE dateString = :dateString LIMIT 1")
    fun getCheckInByDate(dateString: String): Flow<CheckInEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckIn(checkIn: CheckInEntity): Long

    @Update
    suspend fun updateCheckIn(checkIn: CheckInEntity)
}
