package com.example.service

import com.example.model.CanonicalCategories
import com.example.model.CarpetFabricType
import com.example.model.CarpetItem
import com.example.model.ItemCondition
import com.example.model.ListingIntent
import com.example.model.ListingItem
import com.example.model.ListingType
import com.example.model.ProductItem
import com.example.model.ProductionMethod
import com.example.model.VerificationLevel

/**
 * Backward-compatible migration adapter between legacy listings and the extensible [ProductItem] model.
 */
object MarketplaceListingMigration {

    /**
     * Converts a legacy [ListingItem] into an extensible [ProductItem].
     */
    fun toProductItem(legacy: ListingItem): ProductItem {
        val (primaryCat, subCat) = mapLegacyFabricToCategory(legacy.fabricType, legacy.title)
        val intent = when (legacy.listingType) {
            ListingType.BUY_REQUEST -> ListingIntent.BUY_REQUEST
            ListingType.SELL_LISTING -> ListingIntent.SELL_OFFER
            ListingType.BUSINESS_PROMOTION -> ListingIntent.SELL_OFFER
        }

        val tags = mutableListOf<String>()
        if (legacy.fabricType == CarpetFabricType.SERVICES_ONLY) {
            tags.add("خدمات تخصصی")
            legacy.serviceCategory?.let { tags.add(it.titleFa) }
        }
        if (legacy.isPriceNegotiable) {
            tags.add("قیمت توافقی")
        }

        val prodMethod = when (legacy.fabricType) {
            CarpetFabricType.HANDMADE -> ProductionMethod.HANDMADE_KNOTTED
            CarpetFabricType.MACHINE_MADE -> ProductionMethod.MACHINE_WOVEN
            CarpetFabricType.SERVICES_ONLY -> ProductionMethod.UNKNOWN
        }

        // Clean price extraction if numeric
        val numericPrice = legacy.priceText
            .filter { it.isDigit() }
            .toLongOrNull()

        return ProductItem(
            id = legacy.id,
            title = legacy.title,
            description = legacy.description,
            primaryCategoryId = primaryCat,
            subcategoryId = subCat,
            tags = tags,
            imageResIds = if (legacy.imageResId != null) listOf(legacy.imageResId) else emptyList(),
            dimensions = legacy.dimensions.ifBlank { null },
            rawMaterials = legacy.machineSpecs?.yarnMaterial,
            productionMethod = prodMethod,
            condition = ItemCondition.UNSPECIFIED,
            verifiedOriginRegion = null, // Never assume or guess origin unless verified
            priceToman = numericPrice,
            isPriceNegotiable = legacy.isPriceNegotiable,
            isPriceOnCall = legacy.priceText.contains("توافقی") || numericPrice == null,
            intent = intent,
            sellerUserId = legacy.ownerUserId,
            sellerName = legacy.ownerName,
            sellerPhoneMasked = legacy.ownerPhoneMasked,
            sellerProvince = legacy.province.ifBlank { null },
            sellerCity = legacy.city.ifBlank { null },
            sellerVerificationLevel = legacy.ownerVerificationLevel,
            boothId = legacy.boothId,
            isHandmadeCarpetBoothApproved = legacy.fabricType == CarpetFabricType.HANDMADE && legacy.boothId != null,
            isMachineCarpetBoothApproved = legacy.fabricType == CarpetFabricType.MACHINE_MADE && legacy.boothId != null,
            creationTimestamp = legacy.creationTimestamp
        )
    }

    /**
     * Converts a masterwork [CarpetItem] into the extensible [ProductItem] model.
     */
    fun toProductItem(carpet: CarpetItem): ProductItem {
        return ProductItem(
            id = carpet.id,
            title = carpet.titleFa,
            description = carpet.storyNarrativeFa,
            primaryCategoryId = CanonicalCategories.CAT_HANDMADE_CARPET,
            subcategoryId = "sub_hm_qali",
            tags = listOf(carpet.stylePatternFa, carpet.knotTypeFa, "رج‌شمار ${carpet.knotDensityRaj}"),
            imageResIds = listOf(carpet.imageResId),
            dimensions = carpet.dimensions,
            rawMaterials = carpet.materialFa,
            productionMethod = ProductionMethod.HANDMADE_KNOTTED,
            condition = if (carpet.storyNarrativeFa.contains("عتیقه") || carpet.knotDensityRaj > 75) ItemCondition.VINTAGE_ANTIQUE else ItemCondition.NEW,
            verifiedOriginRegion = carpet.originCityFa, // Verified origin from certified masterwork
            priceToman = carpet.priceToman,
            priceUsd = carpet.priceUsd,
            isPriceNegotiable = false,
            isPriceOnCall = false,
            intent = ListingIntent.SELL_OFFER,
            sellerUserId = "merchant_${carpet.merchantName.hashCode()}",
            sellerName = carpet.merchantName,
            sellerPhoneMasked = carpet.merchantPhone,
            sellerProvince = null,
            sellerCity = carpet.merchantLocation,
            sellerVerificationLevel = if (carpet.merchantVerified) VerificationLevel.GUILD_VERIFIED else VerificationLevel.PHONE_OTP_VERIFIED,
            creationTimestamp = carpet.timestamp
        )
    }

    private fun mapLegacyFabricToCategory(fabric: CarpetFabricType, title: String): Pair<String, String?> {
        val lower = title.lowercase()
        return when {
            lower.contains("تابلو") -> CanonicalCategories.CAT_TABLEAU_CARPET to null
            lower.contains("گلیم") -> CanonicalCategories.CAT_KILIM to null
            lower.contains("جاجیم") -> CanonicalCategories.CAT_JAJIM to null
            lower.contains("زیلو") -> CanonicalCategories.CAT_ZILU to null
            lower.contains("خورجین") -> CanonicalCategories.CAT_KHORJIN_JAWAL to null
            fabric == CarpetFabricType.HANDMADE -> CanonicalCategories.CAT_HANDMADE_CARPET to "sub_hm_qalicheh"
            fabric == CarpetFabricType.MACHINE_MADE -> CanonicalCategories.CAT_MACHINE_CARPET to "sub_mc_1200"
            fabric == CarpetFabricType.SERVICES_ONLY -> CanonicalCategories.CAT_OTHER_WOVEN_HERITAGE to null
            else -> CanonicalCategories.CAT_HANDMADE_CARPET to null
        }
    }
}
