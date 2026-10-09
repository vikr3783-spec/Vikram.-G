package com.example.data

import com.example.data.local.entity.DateNightEntity
import com.example.data.local.entity.LoveCouponEntity
import com.example.data.local.entity.MemoryEntity

object SeedData {

    fun getDefaultCoupons(): List<LoveCouponEntity> = listOf(
        LoveCouponEntity(
            title = "Breakfast in Bed with Coffee",
            description = "Valid for warm pancakes, fresh fruit, and your favorite hot brew served directly in bed with a morning kiss.",
            category = "Food"
        ),
        LoveCouponEntity(
            title = "30-Minute Full Body Massage",
            description = "Unwind with essential oils, dimmed lights, soothing music, and a relaxing massage tailored just for you.",
            category = "Pampering"
        ),
        LoveCouponEntity(
            title = "Movie Night Veto & Snack Queen/King",
            description = "You choose the movie, the couch pillows, and all the snacks. Absolutely zero complaints allowed!",
            category = "Cozy"
        ),
        LoveCouponEntity(
            title = "Get Out of One Chore Free",
            description = "Surrender dishwashing, trash duty, or laundry to your partner for the day with a grateful smile.",
            category = "Spontaneous"
        ),
        LoveCouponEntity(
            title = "Candlelit Homemade Dinner",
            description = "A 2-course meal cooked from scratch by your partner with candlelight, soft jazz, and dessert.",
            category = "Food"
        ),
        LoveCouponEntity(
            title = "Spontaneous Midnight Drive & Dessert",
            description = "Late night drive with your favorite playlist, stargazing, and an impromptu stop for milkshakes or ice cream.",
            category = "Adventure"
        ),
        LoveCouponEntity(
            title = "10 Genuine Compliments Storm",
            description = "Redeem at any moment to receive ten heartfelt, specific reasons why you are deeply loved and admired.",
            category = "Romance"
        ),
        LoveCouponEntity(
            title = "Uninterrupted Afternoon Nap Together",
            description = "Two hours of pure cozy spooning, cozy blankets, zero phones, and deep restful slumber.",
            category = "Cozy"
        ),
        LoveCouponEntity(
            title = "Chef's Kiss Picnic in the Park",
            description = "A packed basket with cheese, crackers, berries, a picnic blanket, and your favorite outdoor spot.",
            category = "Adventure"
        ),
        LoveCouponEntity(
            title = "End of Argument White Flag",
            description = "Instant truce, big hug, and a reset button. Valid for letting go of stubbornness and choosing love first.",
            category = "Romance"
        )
    )

    fun getDefaultDateNights(): List<DateNightEntity> = listOf(
        DateNightEntity(
            title = "Living Room Stargazing Fort",
            description = "Build an elaborate blanket fort with fairy lights, pop popcorn, and project a movie or play ambient vinyl records.",
            category = "Cozy Home",
            costIndicator = "$"
        ),
        DateNightEntity(
            title = "Secret Speakeasy Cocktail Tasting",
            description = "Dress up in your finest outfit and explore a hidden speakeasy or craft cocktail lounge with jazz vibes.",
            category = "Dining",
            costIndicator = "$$$"
        ),
        DateNightEntity(
            title = "Sunset Scenic Overlook Picnic",
            description = "Pack takeout tacos or bakery pastries and drive up to the highest viewpoint in town right before golden hour.",
            category = "Outdoor",
            costIndicator = "$"
        ),
        DateNightEntity(
            title = "Bookstore Treasure Hunt",
            description = "Visit a local bookstore. Spend 30 minutes finding: a book you love, one that reminds you of them, and one to read together.",
            category = "Playful",
            costIndicator = "$"
        ),
        DateNightEntity(
            title = "Homemade Pasta Making Workshop",
            description = "Put on matching aprons, pour red wine, and roll fresh handmade fettuccine or gnocchi from scratch.",
            category = "Cozy Home",
            costIndicator = "$$"
        ),
        DateNightEntity(
            title = "Spontaneous Coin-Flip Roadtrip",
            description = "Get in the car. At every intersection, flip a coin (heads right, tails left) 7 times and explore where you arrive!",
            category = "Adventure",
            costIndicator = "$$"
        ),
        DateNightEntity(
            title = "Late Night Board Game Marathon",
            description = "Pick 3 cozy two-player games, light a cinnamon candle, and play for playful stakes like foot rubs or breakfast duty.",
            category = "Playful",
            costIndicator = "$"
        ),
        DateNightEntity(
            title = "Art Museum & Quiet Conversation",
            description = "Stroll slowly through an art gallery, sharing your favorite interpretations and holding hands.",
            category = "Dining",
            costIndicator = "$$"
        )
    )

    fun getDefaultMemories(baseTime: Long): List<MemoryEntity> {
        val dayMillis = 24L * 60L * 60L * 1000L
        return listOf(
            MemoryEntity(
                title = "The Night We First Met",
                note = "Rain was tapping against the café windows. We talked until the barista flipped the sign to closed, and neither of us wanted to leave.",
                location = "Cornerstone Bistro",
                dateMillis = baseTime - (101L * dayMillis),
                mood = "Romantic",
                daysCountAtMoment = 0,
                isPrimeMilestone = false
            ),
            MemoryEntity(
                title = "Day 17 Prime Milestone: First Stargazing Trip",
                note = "Drove outside the city with a wool blanket and hot cocoa. We spotted two shooting stars and made secret wishes.",
                location = "Lookout Mountain",
                dateMillis = baseTime - ((101L - 17L) * dayMillis),
                mood = "Adventure",
                daysCountAtMoment = 17,
                isPrimeMilestone = true
            ),
            MemoryEntity(
                title = "Day 73 Prime Milestone: The Cooking Disaster",
                note = "We attempted soufflé and it collapsed completely, ending up ordering midnight pizza while laughing until our stomachs hurt.",
                location = "Our Kitchen",
                dateMillis = baseTime - ((101L - 73L) * dayMillis),
                mood = "Silly",
                daysCountAtMoment = 73,
                isPrimeMilestone = true
            )
        )
    }

    val dailyAffirmations = listOf(
        "\"In all the world, there is no heart for me like yours. In all the world, there is no love for you like mine.\" — Maya Angelou",
        "Prime connection is choosing each other, not out of habit, but with daily intention and wonder.",
        "\"Whatever our souls are made of, his and mine are the same.\" — Emily Brontë",
        "True intimacy isn't just passion; it's feeling safe enough to be completely yourself.",
        "\"I love you not only for what you are, but for what I am when I am with you.\" — Roy Croft",
        "Every shared laugh is a prime moment stitched into the tapestry of our shared life.",
        "\"You are my today and all of my tomorrows.\" — Leo Christopher"
    )

    val checkInSparks = listOf(
        Pair("Deep Connection", "What is one small thing I did recently that made you feel deeply cherished or appreciated?"),
        Pair("Gratitude", "What is your favorite quiet moment we shared this past week?"),
        Pair("Pillow Talk", "When do you feel most comfortable and at peace in my arms?"),
        Pair("Fun & Playful", "If we could teleport anywhere tonight for 2 hours with unlimited budget, where are we going?"),
        Pair("Future Dreams", "What is a dream you haven't mentioned in a while that you still hold in your heart?"),
        Pair("Deep Connection", "What is something you're currently carrying or stressing over that I can help lighten for you?"),
        Pair("Memories", "What is the exact moment you realized you were falling in love with me?"),
        Pair("Pillow Talk", "What is your favorite nickname, touch, or subtle gesture of mine?"),
        Pair("Fun & Playful", "If our relationship was a hit song or movie title, what would it be right now?")
    )
}
