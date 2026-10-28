package com.example.service

import com.example.model.BoothType
import com.example.model.CanonicalCategories
import com.example.model.MerchantBooth
import com.example.model.ProductCategory
import com.example.model.ProductItem

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errors: List<String>) : ValidationResult() {
        val errorMessageFa: String get() = errors.joinToString("\n")
    }
}

/**
 * Validates product submissions against marketplace policies and domain constraints.
 */
object ProductValidator {

    fun validate(item: ProductItem, activeCategories: List<ProductCategory>): ValidationResult {
        val errors = mutableListOf<String>()

        // 1. Title validation
        if (item.title.trim().length < 4) {
            errors.add("عنوان کالا/آگهی باید حداقل ۴ حرف باشد.")
        }

        // 2. Description validation
        if (item.description.trim().length < 10) {
            errors.add("توضیحات کالا باید حداقل حاوی ۱۰ حرف باشد.")
        }

        // 3. Single Primary Category constraint
        if (item.primaryCategoryId.isBlank()) {
            errors.add("انتخاب دقیق یک دستهٔ اصلی الزامی است.")
        } else {
            val matchingCat = activeCategories.firstOrNull { it.id == item.primaryCategoryId }
            if (matchingCat == null) {
                errors.add("دسته اصلی انتخاب‌شده معتبر یا فعال نیست.")
            } else if (!matchingCat.isEnabled) {
                errors.add("دسته انتخابی در حال حاضر غیرفعال است.")
            }
        }

        // 4. Subcategory validation (if provided)
        if (!item.subcategoryId.isNullOrBlank()) {
            val cat = activeCategories.firstOrNull { it.id == item.primaryCategoryId }
            if (cat != null && cat.subcategories.none { it.id == item.subcategoryId && it.isEnabled }) {
                errors.add("زیردسته انتخاب‌شده با دستهٔ اصلی مطابقت ندارد یا غیرفعال است.")
            }
        }

        // 5. Price sanity check (optional, but if provided must be positive)
        if (item.priceToman != null && item.priceToman < 0) {
            errors.add("مبلغ قیمت نمی‌تواند عددی منفی باشد.")
        }

        // 6. Unknown Origin rule
        // Origin must never be forced or guessed; if provided, it cannot be a meaningless placeholder
        if (item.verifiedOriginRegion != null && item.verifiedOriginRegion.trim().length < 2) {
            errors.add("در صورت ذکر منطقه تولید، باید نام معتبر شهر یا منطقه قید شود (یا خالی رها شود).")
        }

        // 7. Seller identification
        if (item.sellerUserId.isBlank() || item.sellerName.isBlank()) {
            errors.add("اطلاعات ثبت‌کننده آگهی ناقص است.")
        }

        return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
    }
}

/**
 * Enforces booth permissions and category access rules:
 * - Specialized carpet booths (Handmade Pavilion & Machine-made Pavilion) require official guild verification.
 * - Non-carpet woven crafts (kilim, jajim, zilu, khorjin, namakdan, saddle covers, etc.) can be listed
 *   freely by craftspeople and sellers in their respective categories.
 * - Listing other woven crafts NEVER automatically confers permission or status for carpet pavilions.
 */
object BoothPermissionValidator {

    /**
     * Checks if a category requires specialized carpet booth authorization.
     */
    fun isSpecializedCarpetCategory(categoryId: String): Boolean {
        return categoryId == CanonicalCategories.CAT_HANDMADE_CARPET ||
            categoryId == CanonicalCategories.CAT_MACHINE_CARPET
    }

    /**
     * Verifies if a booth is authorized to publish in the specified category.
     */
    fun canBoothPublishInCategory(booth: MerchantBooth?, categoryId: String): Boolean {
        if (booth == null) return true // Individual sellers can publish standard ads

        return when (categoryId) {
            CanonicalCategories.CAT_HANDMADE_CARPET -> {
                booth.boothType == BoothType.HANDMADE_PAVILION && booth.isGuildVerified
            }
            CanonicalCategories.CAT_MACHINE_CARPET -> {
                booth.boothType == BoothType.MACHINE_MADE_PAVILION && booth.isGuildVerified
            }
            else -> {
                // Other woven crafts can be published in their respective category
                // without requiring specialized carpet booth licensing.
                true
            }
        }
    }

    /**
     * Validates whether publishing in other crafts grants booth authorization.
     * Always returns false to protect carpet pavilion sanctity.
     */
    fun doesCraftListingGrantCarpetBoothAuthorization(categoryId: String): Boolean {
        return false
    }
}
