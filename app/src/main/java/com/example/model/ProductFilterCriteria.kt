package com.example.model

/**
 * Filter and search criteria for Farshbazar marketplace.
 */
data class ProductFilterCriteria(
    val searchQuery: String? = null,
    val categoryId: String? = null,
    val subcategoryId: String? = null,
    val intent: ListingIntent? = null,
    val minPriceToman: Long? = null,
    val maxPriceToman: Long? = null,
    val dimensionsQuery: String? = null,
    val rawMaterialQuery: String? = null,
    val provinceOrCity: String? = null,
    val condition: ItemCondition? = null,
    val productionMethod: ProductionMethod? = null,
    val onlyVerifiedSellers: Boolean = false,
    val tag: String? = null
)

/**
 * Query and filter engine operating on [ProductItem] collections.
 */
object ProductFilterEngine {

    fun matches(item: ProductItem, criteria: ProductFilterCriteria): Boolean {
        // 1. Category Filter (Item belongs to exactly one primary category)
        if (criteria.categoryId != null && item.primaryCategoryId != criteria.categoryId) {
            return false
        }

        // 2. Subcategory Filter
        if (criteria.subcategoryId != null && item.subcategoryId != criteria.subcategoryId) {
            return false
        }

        // 3. Intent Filter (Strict separation between Buy Requests and Sell Offers)
        if (criteria.intent != null && item.intent != criteria.intent) {
            return false
        }

        // 4. Price Range (Only if item price is present)
        if (criteria.minPriceToman != null) {
            val itemPrice = item.priceToman ?: return false
            if (itemPrice < criteria.minPriceToman) return false
        }
        if (criteria.maxPriceToman != null) {
            val itemPrice = item.priceToman ?: return false
            if (itemPrice > criteria.maxPriceToman) return false
        }

        // 5. Dimensions Filter
        if (!criteria.dimensionsQuery.isNullOrBlank()) {
            val itemDim = item.dimensions ?: return false
            if (!itemDim.contains(criteria.dimensionsQuery.trim(), ignoreCase = true)) {
                return false
            }
        }

        // 6. Raw Materials Filter
        if (!criteria.rawMaterialQuery.isNullOrBlank()) {
            val itemMat = item.rawMaterials ?: return false
            if (!itemMat.contains(criteria.rawMaterialQuery.trim(), ignoreCase = true)) {
                return false
            }
        }

        // 7. Seller Location Filter (Province / City)
        if (!criteria.provinceOrCity.isNullOrBlank()) {
            val query = criteria.provinceOrCity.trim()
            val matchesCity = item.sellerCity?.contains(query, ignoreCase = true) == true
            val matchesProvince = item.sellerProvince?.contains(query, ignoreCase = true) == true
            if (!matchesCity && !matchesProvince) return false
        }

        // 8. Condition Filter
        if (criteria.condition != null && item.condition != criteria.condition) {
            return false
        }

        // 9. Production Method Filter
        if (criteria.productionMethod != null && item.productionMethod != criteria.productionMethod) {
            return false
        }

        // 10. Verified Seller Only
        if (criteria.onlyVerifiedSellers) {
            val isVerified = item.sellerVerificationLevel == VerificationLevel.SIM_OWNERSHIP_VERIFIED ||
                item.sellerVerificationLevel == VerificationLevel.GUILD_VERIFIED
            if (!isVerified) return false
        }

        // 11. Tag Filter
        if (!criteria.tag.isNullOrBlank()) {
            if (!item.tags.any { it.equals(criteria.tag.trim(), ignoreCase = true) }) {
                return false
            }
        }

        // 12. Full-Text Search Query (Title, Description, Tags, Origin)
        if (!criteria.searchQuery.isNullOrBlank()) {
            val q = criteria.searchQuery.trim().lowercase()
            val inTitle = item.title.lowercase().contains(q)
            val inDesc = item.description.lowercase().contains(q)
            val inTags = item.tags.any { it.lowercase().contains(q) }
            val inOrigin = item.verifiedOriginRegion?.lowercase()?.contains(q) == true
            val inSeller = item.sellerName.lowercase().contains(q)
            if (!inTitle && !inDesc && !inTags && !inOrigin && !inSeller) {
                return false
            }
        }

        return true
    }

    fun filter(items: List<ProductItem>, criteria: ProductFilterCriteria): List<ProductItem> {
        return items.filter { matches(it, criteria) }
    }
}
