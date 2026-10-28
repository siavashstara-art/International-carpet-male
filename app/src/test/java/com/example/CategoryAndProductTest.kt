package com.example

import com.example.model.BoothType
import com.example.model.CanonicalCategories
import com.example.model.CarpetFabricType
import com.example.model.ItemCondition
import com.example.model.ListingIntent
import com.example.model.ListingItem
import com.example.model.ListingStatus
import com.example.model.ListingType
import com.example.model.MerchantBooth
import com.example.model.ProductCategory
import com.example.model.ProductFilterCriteria
import com.example.model.ProductFilterEngine
import com.example.model.ProductItem
import com.example.model.ProductSubcategory
import com.example.model.ProductionMethod
import com.example.model.VerificationLevel
import com.example.service.BoothPermissionValidator
import com.example.service.DynamicCategoryRegistry
import com.example.service.MarketplaceListingMigration
import com.example.service.ProductValidator
import com.example.service.ValidationResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CategoryAndProductTest {

    @Test
    fun `verify 15 canonical categories are registered and enabled by default`() {
        val categories = CanonicalCategories.DEFAULT_CATEGORIES
        assertEquals("Marketplace must have exactly 15 primary canonical categories", 15, categories.size)

        val expectedIds = listOf(
            CanonicalCategories.CAT_HANDMADE_CARPET,
            CanonicalCategories.CAT_MACHINE_CARPET,
            CanonicalCategories.CAT_TABLEAU_CARPET,
            CanonicalCategories.CAT_KILIM,
            CanonicalCategories.CAT_JAJIM,
            CanonicalCategories.CAT_ZILU,
            CanonicalCategories.CAT_VERNEH_FLATWEAVE,
            CanonicalCategories.CAT_KHORJIN_JAWAL,
            CanonicalCategories.CAT_NAMAKDAN_STORAGE,
            CanonicalCategories.CAT_TRADITIONAL_SOFREH,
            CanonicalCategories.CAT_HORSE_SADDLE_COVER,
            CanonicalCategories.CAT_CUSHION_POSHTI_DECOR,
            CanonicalCategories.CAT_BAG_SHOE_ACCESSORIES,
            CanonicalCategories.CAT_VESSEL_COVERS_FUNCTIONAL,
            CanonicalCategories.CAT_OTHER_WOVEN_HERITAGE
        )

        for (id in expectedIds) {
            val cat = categories.firstOrNull { it.id == id }
            assertNotNull("Category with id $id must exist in canonical registry", cat)
            assertTrue("Default category $id must be enabled", cat!!.isEnabled)
            assertTrue("Title must not be blank", cat.titleFa.isNotBlank())
        }
    }

    @Test
    fun `verify dynamic category addition update and deactivation without app update`() = runBlocking {
        val registry = DynamicCategoryRegistry()
        val initialCount = registry.getActiveCategories().size
        assertEquals(15, initialCount)

        // 1. Unauthorized modification attempt must fail
        val newCat = ProductCategory(
            id = "cat_nomadic_felt",
            titleFa = "نمد و بافته‌های نمدی سنتی",
            descriptionFa = "زیراندازها و کلاه‌مالی‌های نمدی عشایری",
            iconIdentifier = "ic_felt",
            orderIndex = 16
        )
        val unauthorizedResult = registry.addCategory(newCat, adminToken = "invalid_token")
        assertTrue("Unauthorized addition must fail", unauthorizedResult.isFailure)

        // 2. Authorized dynamic addition
        val authorizedResult = registry.addCategory(newCat, adminToken = "FARSHBAZAR_SUPERADMIN")
        assertTrue("Authorized addition must succeed", authorizedResult.isSuccess)
        assertEquals(initialCount + 1, registry.getActiveCategories().size)

        // 3. Dynamic deactivation
        val deactivateResult = registry.toggleCategoryStatus("cat_nomadic_felt", isEnabled = false, adminToken = "FARSHBAZAR_SUPERADMIN")
        assertTrue(deactivateResult.isSuccess)
        assertEquals(initialCount, registry.getActiveCategories().size)
        assertEquals(initialCount + 1, registry.getAllCategories(includeDisabled = true).size)

        // 4. Dynamic subcategory addition
        val subCat = ProductSubcategory(
            id = "sub_felt_shahsevan",
            categoryId = "cat_nomadic_felt",
            titleFa = "نمد شاهسون و مغان",
            orderIndex = 1
        )
        val addSubResult = registry.addSubcategory("cat_nomadic_felt", subCat, adminToken = "FARSHBAZAR_SUPERADMIN")
        assertTrue(addSubResult.isSuccess)
        val fetched = registry.getCategoryById("cat_nomadic_felt")
        assertEquals(1, fetched?.subcategories?.size)
    }

    @Test
    fun `verify single primary category constraint and multi-tag support`() {
        val activeCategories = CanonicalCategories.DEFAULT_CATEGORIES

        // Valid product: exactly one primary category with multiple tags
        val validProduct = ProductItem(
            id = "prod_1",
            title = "گلیم دستباف هرسین با رنگرزی روناس و نیل",
            description = "گلیم کهن‌الگو هرسین دستباف با نقوش حیوانی و بوته، تمام پشم طبیعی",
            primaryCategoryId = CanonicalCategories.CAT_KILIM,
            tags = listOf("هرسین", "رنگرزی گیاهی", "تمام پشم", "صادراتی"),
            sellerUserId = "user_craft_1",
            sellerName = "استاد رضایی",
            sellerPhoneMasked = "۰۹۱۲****۳۳۳"
        )
        val result = ProductValidator.validate(validProduct, activeCategories)
        assertTrue("Valid single category and multiple tags must be valid", result is ValidationResult.Valid)

        // Invalid: missing primary category
        val invalidProduct = validProduct.copy(primaryCategoryId = "")
        val invalidResult = ProductValidator.validate(invalidProduct, activeCategories)
        assertTrue("Missing category must fail validation", invalidResult is ValidationResult.Invalid)

        // Invalid: non-existent category
        val nonExistentCatProduct = validProduct.copy(primaryCategoryId = "cat_non_existent")
        val nonExistentResult = ProductValidator.validate(nonExistentCatProduct, activeCategories)
        assertTrue("Non-existent category must fail validation", nonExistentResult is ValidationResult.Invalid)
    }

    @Test
    fun `verify unknown attributes are optional and never guessed`() {
        val activeCategories = CanonicalCategories.DEFAULT_CATEGORIES

        // Product with unverified/unknown dimensions, material, and origin
        val productWithUnknowns = ProductItem(
            id = "prod_unknown_specs",
            title = "خورجین قدیمی عشایری دوطرفه پشمی",
            description = "خورجین نگهداری وسایل بافته‌شده به روش قلیچی و شیریکی بدون درج ابعاد دقیق یا منشأ نامشخص",
            primaryCategoryId = CanonicalCategories.CAT_KHORJIN_JAWAL,
            dimensions = null, // Unknown - optional
            rawMaterials = null, // Unknown - optional
            verifiedOriginRegion = null, // MUST NOT be guessed
            priceToman = null, // Price on call
            isPriceOnCall = true,
            sellerUserId = "user_collector_5",
            sellerName = "مجموعه‌دار سنتی",
            sellerPhoneMasked = "۰۹۳۵****۸۸۸"
        )

        val result = ProductValidator.validate(productWithUnknowns, activeCategories)
        assertTrue("Item with optional unknown specs must validate cleanly without guessing", result is ValidationResult.Valid)
        assertNull("Origin must remain null rather than being artificially guessed", productWithUnknowns.verifiedOriginRegion)
    }

    @Test
    fun `verify strict separation of buyer requests from seller offers`() {
        val buyRequest = ProductItem(
            id = "buy_1",
            title = "تقاضای خرید زیلو ۱۰ متری میبد یزد",
            description = "خریدار نقدی زیلو سالم و خوش‌رنگ برای اقامتگاه بوم‌گردی کاشان",
            primaryCategoryId = CanonicalCategories.CAT_ZILU,
            intent = ListingIntent.BUY_REQUEST,
            sellerUserId = "buyer_10",
            sellerName = "اقامتگاه سنتی مهر",
            sellerPhoneMasked = "۰۹۳۶****۲۲۲"
        )

        val sellOffer = ProductItem(
            id = "sell_1",
            title = "فروش زیلو پنبه‌ای اصل میبد ۸ متری",
            description = "زیلو نو دست‌اول پنبه‌ای دورو با بافت استاد بافنده میبدی",
            primaryCategoryId = CanonicalCategories.CAT_ZILU,
            intent = ListingIntent.SELL_OFFER,
            sellerUserId = "seller_20",
            sellerName = "هنرمند میبدی",
            sellerPhoneMasked = "۰۹۱۵****۱۱۱"
        )

        val allItems = listOf(buyRequest, sellOffer)

        // Query only buy requests
        val buyersOnly = ProductFilterEngine.filter(allItems, ProductFilterCriteria(intent = ListingIntent.BUY_REQUEST))
        assertEquals(1, buyersOnly.size)
        assertEquals(ListingIntent.BUY_REQUEST, buyersOnly.first().intent)

        // Query only sell offers
        val sellersOnly = ProductFilterEngine.filter(allItems, ProductFilterCriteria(intent = ListingIntent.SELL_OFFER))
        assertEquals(1, sellersOnly.size)
        assertEquals(ListingIntent.SELL_OFFER, sellersOnly.first().intent)
    }

    @Test
    fun `verify booth permission isolation between specialized carpets and other crafts`() {
        // Individual seller or craft seller
        val craftSellerBooth: MerchantBooth? = null

        // Other craft categories (kilim, jajim, khorjin, namakdan, saddle cover) can be listed freely
        assertTrue(
            "Kilim should be freely publishable without carpet pavilion booth",
            BoothPermissionValidator.canBoothPublishInCategory(craftSellerBooth, CanonicalCategories.CAT_KILIM)
        )
        assertTrue(
            "Jajim should be freely publishable without carpet pavilion booth",
            BoothPermissionValidator.canBoothPublishInCategory(craftSellerBooth, CanonicalCategories.CAT_JAJIM)
        )
        assertTrue(
            "Khorjin should be freely publishable without carpet pavilion booth",
            BoothPermissionValidator.canBoothPublishInCategory(craftSellerBooth, CanonicalCategories.CAT_KHORJIN_JAWAL)
        )

        // Crucial rule: listing other crafts NEVER grants carpet booth status
        assertFalse(
            "Listing other crafts must never grant carpet booth authorization",
            BoothPermissionValidator.doesCraftListingGrantCarpetBoothAuthorization(CanonicalCategories.CAT_KILIM)
        )

        // Specialized carpet booths validation
        val handmadeBooth = MerchantBooth(
            id = "booth_tabriz_1",
            ownerUserId = "usr_master_1",
            boothType = BoothType.HANDMADE_PAVILION,
            title = "غرفه قالی دستباف تبریز",
            city = "تبریز",
            marketAddress = "تیمچه مظفریه",
            isGuildVerified = true,
            contactPhoneMasked = "۰۴۱****۹۹۹"
        )

        val machineBooth = MerchantBooth(
            id = "booth_kashan_2",
            ownerUserId = "usr_kashan_2",
            boothType = BoothType.MACHINE_MADE_PAVILION,
            title = "غرفه فرش نگین کاشان",
            city = "کاشان",
            marketAddress = "شهرک صنعتی راوند",
            isGuildVerified = true,
            contactPhoneMasked = "۰۳۱****۸۸۸"
        )

        assertTrue(
            "Handmade booth can publish in handmade carpet category",
            BoothPermissionValidator.canBoothPublishInCategory(handmadeBooth, CanonicalCategories.CAT_HANDMADE_CARPET)
        )
        assertFalse(
            "Machine-made booth cannot publish in handmade carpet pavilion",
            BoothPermissionValidator.canBoothPublishInCategory(machineBooth, CanonicalCategories.CAT_HANDMADE_CARPET)
        )
    }

    @Test
    fun `verify search and filter engine across multiple criteria`() {
        val item1 = ProductItem(
            id = "item_1",
            title = "جاجیم دستباف خلخال پشمی",
            description = "جاجیم سنتی ۴ تخته رنگ گیاهی خلخال بسیار چشم‌نواز",
            primaryCategoryId = CanonicalCategories.CAT_JAJIM,
            dimensions = "۲۰۰ × ۲۰۰ سانتی‌متر",
            rawMaterials = "پشم خالص دست‌ریس",
            sellerProvince = "اردبیل",
            sellerCity = "خلخال",
            priceToman = 8_500_000L,
            sellerUserId = "usr_1",
            sellerName = "بافنده اردبیلی",
            sellerPhoneMasked = "۰۹۱۴****۱۱۱",
            sellerVerificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED
        )

        val item2 = ProductItem(
            id = "item_2",
            title = "ورنی سنتی اصل دشت مغان نقشه سماقی",
            description = "ورنی ابریشم و پشم ظریف با نقوش حیوانات وحشی و کبوتر",
            primaryCategoryId = CanonicalCategories.CAT_VERNEH_FLATWEAVE,
            dimensions = "۱۵۰ × ۲۰۰ سانتی‌متر",
            rawMaterials = "پشم و ابریشم",
            sellerProvince = "اردبیل",
            sellerCity = "پارس‌آباد",
            priceToman = 25_000_000L,
            sellerUserId = "usr_2",
            sellerName = "هنرمند عشایری",
            sellerPhoneMasked = "۰۹۱۴****۲۲۲",
            sellerVerificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED
        )

        val item3 = ProductItem(
            id = "item_3",
            title = "پشتی سنتی قالیچه‌ای کاشان جفت",
            description = "جفت پشتی پرشده با کرک و پشم سنتی بافت کاشان",
            primaryCategoryId = CanonicalCategories.CAT_CUSHION_POSHTI_DECOR,
            dimensions = "۶۰ × ۸۰ سانتی‌متر",
            rawMaterials = "پشم و کرک",
            sellerProvince = "اصفهان",
            sellerCity = "کاشان",
            priceToman = 5_000_000L,
            sellerUserId = "usr_3",
            sellerName = "فروشنده کاشانی",
            sellerPhoneMasked = "۰۳۱****۳۳۳",
            sellerVerificationLevel = VerificationLevel.UNVERIFIED
        )

        val items = listOf(item1, item2, item3)

        // 1. Filter by category
        val jajimOnly = ProductFilterEngine.filter(items, ProductFilterCriteria(categoryId = CanonicalCategories.CAT_JAJIM))
        assertEquals(1, jajimOnly.size)
        assertEquals("item_1", jajimOnly.first().id)

        // 2. Filter by price range (min 6M, max 10M)
        val priceFiltered = ProductFilterEngine.filter(items, ProductFilterCriteria(minPriceToman = 6_000_000L, maxPriceToman = 10_000_000L))
        assertEquals(1, priceFiltered.size)
        assertEquals("item_1", priceFiltered.first().id)

        // 3. Filter by location (Ardabil)
        val ardabilItems = ProductFilterEngine.filter(items, ProductFilterCriteria(provinceOrCity = "اردبیل"))
        assertEquals(2, ardabilItems.size)

        // 4. Filter by material (ابریشم)
        val silkItems = ProductFilterEngine.filter(items, ProductFilterCriteria(rawMaterialQuery = "ابریشم"))
        assertEquals(1, silkItems.size)
        assertEquals("item_2", silkItems.first().id)

        // 5. Filter by verified sellers only
        val verifiedOnly = ProductFilterEngine.filter(items, ProductFilterCriteria(onlyVerifiedSellers = true))
        assertEquals(2, verifiedOnly.size)

        // 6. Search query "خلخال"
        val searched = ProductFilterEngine.filter(items, ProductFilterCriteria(searchQuery = "خلخال"))
        assertEquals(1, searched.size)
        assertEquals("item_1", searched.first().id)
    }

    @Test
    fun `verify marketplace listing migration adapter compatibility`() {
        val legacyListing = ListingItem(
            id = "leg_1",
            ownerUserId = "usr_legacy_10",
            ownerName = "علی صراف",
            ownerPhoneMasked = "۰۹۱۲****۹۹۹",
            ownerVerificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED,
            listingType = ListingType.BUY_REQUEST,
            fabricType = CarpetFabricType.HANDMADE,
            title = "خریدار گلیم هرسین",
            description = "تقاضای گلیم هرسین زرع و نیم نقشه شطرنجی",
            province = "کرمانشاه",
            city = "هرسین",
            dimensions = "۱۰۰ × ۱۵۰",
            priceText = "۱۰,۰۰۰,۰۰۰ تومان",
            isPriceNegotiable = true,
            status = ListingStatus.PUBLISHED
        )

        val migrated = MarketplaceListingMigration.toProductItem(legacyListing)
        assertEquals(legacyListing.id, migrated.id)
        assertEquals(legacyListing.title, migrated.title)
        assertEquals(CanonicalCategories.CAT_KILIM, migrated.primaryCategoryId)
        assertEquals(ListingIntent.BUY_REQUEST, migrated.intent)
        assertEquals(legacyListing.ownerUserId, migrated.sellerUserId)
        assertEquals(10_000_000L, migrated.priceToman)
        assertTrue(migrated.isPriceNegotiable)
    }
}
