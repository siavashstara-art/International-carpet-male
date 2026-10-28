package com.example.model

/**
 * Star tiers and mathematical conversion rules:
 * Base: Blue Star (ستاره آبی)
 * 7 Blue = 1 Silver (ستاره نقره‌ای)
 * 7 Silver = 1 Gold (ستاره طلایی)
 * 7 Gold = 1 Diamond (ستاره الماس‌نشان)
 * 7 Diamond = 1 Mythic Legend (ستاره اسطوره برنامه)
 */
data class StarBreakdown(
    val mythicStars: Int,
    val diamondStars: Int,
    val goldStars: Int,
    val silverStars: Int,
    val blueStars: Int,
    val totalEquivalentBlueStars: Int
)

object StarUpgradeCalculator {

    /**
     * Converts a raw count of blue stars (from attendance or bonus awards)
     * into exact tiered stars with full surplus retention.
     */
    fun calculateTiers(totalBlueStars: Int): StarBreakdown {
        var remaining = totalBlueStars.coerceAtLeast(0)

        val BLUE_PER_SILVER = 7
        val BLUE_PER_GOLD = BLUE_PER_SILVER * 7 // 49
        val BLUE_PER_DIAMOND = BLUE_PER_GOLD * 7 // 343
        val BLUE_PER_MYTHIC = BLUE_PER_DIAMOND * 7 // 2401

        val mythic = remaining / BLUE_PER_MYTHIC
        remaining %= BLUE_PER_MYTHIC

        val diamond = remaining / BLUE_PER_DIAMOND
        remaining %= BLUE_PER_DIAMOND

        val gold = remaining / BLUE_PER_GOLD
        remaining %= BLUE_PER_GOLD

        val silver = remaining / BLUE_PER_SILVER
        val blue = remaining % BLUE_PER_SILVER

        return StarBreakdown(
            mythicStars = mythic,
            diamondStars = diamond,
            goldStars = gold,
            silverStars = silver,
            blueStars = blue,
            totalEquivalentBlueStars = totalBlueStars
        )
    }

    /**
     * Award stars for Referral League (لیگ معرفان):
     * Rank 1: 4 stars
     * Rank 2-3: 3 stars
     * Rank 4-6: 2 stars
     * Rank 7-10: 1 star
     */
    fun getReferralLeagueBonusStars(rank: Int): Int {
        return when (rank) {
            1 -> 4
            in 2..3 -> 3
            in 4..6 -> 2
            in 7..10 -> 1
            else -> 0
        }
    }

    /**
     * Virtual City of Tavana (شهر مجازی توانا) land quota allocation:
     * Rank 1: 500 m²
     * Rank 2: 450 m²
     * Rank 3: 400 m²
     * Rank 4: 380 m²
     * Rank 5: 350 m²
     * Rank 6: 330 m²
     * Rank 7: 300 m²
     * Rank 8: 280 m²
     * Rank 9: 260 m²
     * Rank 10: 240 m²
     */
    fun getTavanaVirtualLandQuotaSqMeters(rank: Int): Int? {
        return when (rank) {
            1 -> 500
            2 -> 450
            3 -> 400
            4 -> 380
            5 -> 350
            6 -> 330
            7 -> 300
            8 -> 280
            9 -> 260
            10 -> 240
            else -> null
        }
    }
}
