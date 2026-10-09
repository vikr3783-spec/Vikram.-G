package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.SeedData
import com.example.data.local.entity.CheckInEntity
import com.example.data.local.entity.DateNightEntity
import com.example.data.local.entity.LoveCouponEntity
import com.example.data.local.entity.MemoryEntity
import com.example.data.repository.LovePrimeRepository
import com.example.model.CoupleProfile
import com.example.model.PrimeCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class LovePrimeViewModel(
    private val repository: LovePrimeRepository
) : ViewModel() {

    val coupleProfile: StateFlow<CoupleProfile> = repository.coupleProfile

    val coupons: StateFlow<List<LoveCouponEntity>> = repository.coupons
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dateNights: StateFlow<List<DateNightEntity>> = repository.dateNights
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val memories: StateFlow<List<MemoryEntity>> = repository.memories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val checkIns: StateFlow<List<CheckInEntity>> = repository.checkIns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentQuestionIndex = MutableStateFlow(0)
    private val _currentQuestion = MutableStateFlow(SeedData.checkInSparks[0])
    val currentQuestion: StateFlow<Pair<String, String>> = _currentQuestion.asStateFlow()

    private val _dailyQuote = MutableStateFlow(SeedData.dailyAffirmations[0])
    val dailyQuote: StateFlow<String> = _dailyQuote.asStateFlow()

    private val _rouletteResult = MutableStateFlow<DateNightEntity?>(null)
    val rouletteResult: StateFlow<DateNightEntity?> = _rouletteResult.asStateFlow()

    private val _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean> = _isSpinning.asStateFlow()

    private val _currentTimeMillis = MutableStateFlow(System.currentTimeMillis())
    val currentTimeMillis: StateFlow<Long> = _currentTimeMillis.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDatabaseIfNeeded()
            // Set daily quote based on day of year
            val dayIndex = (System.currentTimeMillis() / (24L * 60 * 60 * 1000)) % SeedData.dailyAffirmations.size
            _dailyQuote.value = SeedData.dailyAffirmations[dayIndex.toInt()]
        }

        // Timer ticking every second for real-time together counter
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _currentTimeMillis.value = System.currentTimeMillis()
            }
        }
    }

    fun calculateDaysTogether(anniversaryMillis: Long, now: Long = _currentTimeMillis.value): Long {
        val diff = (now - anniversaryMillis).coerceAtLeast(0)
        return diff / (24L * 60 * 60 * 1000)
    }

    fun calculateTimeBreakdown(anniversaryMillis: Long, now: Long = _currentTimeMillis.value): TimeBreakdown {
        val diff = (now - anniversaryMillis).coerceAtLeast(0)
        val seconds = (diff / 1000) % 60
        val minutes = (diff / (1000 * 60)) % 60
        val hours = (diff / (1000 * 60 * 60)) % 24
        val days = diff / (1000L * 60 * 60 * 24)
        return TimeBreakdown(days, hours, minutes, seconds)
    }

    fun nextQuestion() {
        val nextIdx = (_currentQuestionIndex.value + 1) % SeedData.checkInSparks.size
        _currentQuestionIndex.value = nextIdx
        _currentQuestion.value = SeedData.checkInSparks[nextIdx]
    }

    fun spinDateNightRoulette() {
        val currentDates = dateNights.value
        if (currentDates.isEmpty()) return

        viewModelScope.launch {
            _isSpinning.value = true
            // Quick suspense roulette animation simulation
            repeat(10) { i ->
                _rouletteResult.value = currentDates[Random.nextInt(currentDates.size)]
                delay(80L + (i * 20L))
            }
            _isSpinning.value = false
        }
    }

    fun redeemCoupon(coupon: LoveCouponEntity) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date())
            repository.updateCoupon(
                coupon.copy(
                    isRedeemed = true,
                    redeemedDate = dateStr
                )
            )
        }
    }

    fun resetCoupon(coupon: LoveCouponEntity) {
        viewModelScope.launch {
            repository.updateCoupon(
                coupon.copy(
                    isRedeemed = false,
                    redeemedDate = null
                )
            )
        }
    }

    fun addCustomCoupon(title: String, description: String, category: String) {
        viewModelScope.launch {
            repository.addCoupon(
                LoveCouponEntity(
                    title = title.trim(),
                    description = description.trim(),
                    category = category,
                    isCustom = true
                )
            )
        }
    }

    fun deleteCoupon(coupon: LoveCouponEntity) {
        viewModelScope.launch {
            repository.deleteCoupon(coupon)
        }
    }

    fun addDateNight(title: String, description: String, category: String, cost: String) {
        viewModelScope.launch {
            repository.addDateNight(
                DateNightEntity(
                    title = title.trim(),
                    description = description.trim(),
                    category = category,
                    costIndicator = cost
                )
            )
        }
    }

    fun toggleDateNightCompleted(dateNight: DateNightEntity, rating: Int = 5, notes: String = "") {
        viewModelScope.launch {
            val isNowCompleted = !dateNight.isCompleted
            val dateStr = if (isNowCompleted) {
                SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date())
            } else null

            repository.updateDateNight(
                dateNight.copy(
                    isCompleted = isNowCompleted,
                    completedDate = dateStr,
                    rating = if (isNowCompleted) rating else 0,
                    notes = notes
                )
            )
        }
    }

    fun deleteDateNight(dateNight: DateNightEntity) {
        viewModelScope.launch {
            repository.deleteDateNight(dateNight)
        }
    }

    fun addMemory(title: String, note: String, location: String, mood: String) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val daysCount = calculateDaysTogether(coupleProfile.value.anniversaryDateMillis, now)
            val isPrime = PrimeCalculator.isPrime(daysCount)

            repository.addMemory(
                MemoryEntity(
                    title = title.trim(),
                    note = note.trim(),
                    location = location.trim(),
                    dateMillis = now,
                    mood = mood,
                    daysCountAtMoment = daysCount,
                    isPrimeMilestone = isPrime
                )
            )
        }
    }

    fun deleteMemory(memory: MemoryEntity) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
        }
    }

    fun submitDailyCheckIn(partner1Answer: String, partner2Answer: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val q = currentQuestion.value
            repository.saveCheckIn(
                CheckInEntity(
                    dateString = dateStr,
                    category = q.first,
                    question = q.second,
                    partner1Answer = partner1Answer.trim(),
                    partner2Answer = partner2Answer.trim(),
                    completedAtMillis = System.currentTimeMillis()
                )
            )
        }
    }

    fun updateProfile(
        p1Name: String,
        p2Name: String,
        anniversaryMillis: Long,
        p1LoveLanguage: String,
        p2LoveLanguage: String,
        status: String
    ) {
        viewModelScope.launch {
            repository.updateProfile(
                CoupleProfile(
                    partner1Name = p1Name.trim(),
                    partner2Name = p2Name.trim(),
                    anniversaryDateMillis = anniversaryMillis,
                    partner1LoveLanguage = p1LoveLanguage,
                    partner2LoveLanguage = p2LoveLanguage,
                    customStatusMessage = status.trim()
                )
            )
        }
    }
}

data class TimeBreakdown(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)

class LovePrimeViewModelFactory(
    private val repository: LovePrimeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LovePrimeViewModel(repository) as T
    }
}
