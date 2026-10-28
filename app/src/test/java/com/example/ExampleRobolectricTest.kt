package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CarpetFabricType
import com.example.model.Currency
import com.example.model.ListingItem
import com.example.model.ListingStatus
import com.example.model.ListingType
import com.example.model.QuotaUsage
import com.example.model.StarUpgradeCalculator
import com.example.model.VerificationLevel
import com.example.service.AccountAndMarketplaceRepository
import com.example.service.RajEstimationServiceImpl
import com.example.ui.viewmodel.CarpetViewModel
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
class ExampleRobolectricTest {

  @Test
  fun `verify app name resource matches official Persian title`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("بازار فرش ایران و جهان (دیجیتال مارکت)", appName)
  }

  @Test
  fun `verify carpet viewModel initialization and features`() {
    val viewModel = CarpetViewModel()
    val initialCarpets = viewModel.carpets.value
    assertTrue("Should have initial authentic masterpieces", initialCarpets.isNotEmpty())

    // Test favorite toggling
    val firstId = initialCarpets.first().id
    val initiallyFav = initialCarpets.first().isFavorite
    viewModel.toggleFavorite(firstId)
    val toggledFav = viewModel.carpets.value.first { it.id == firstId }.isFavorite
    assertEquals(!initiallyFav, toggledFav)

    // Test currency pricing formatting
    val carpet = initialCarpets.first()
    val tomanFormatted = viewModel.formatPrice(carpet, Currency.TOMAN)
    val usdFormatted = viewModel.formatPrice(carpet, Currency.USD)
    assertTrue("Toman format should contain currency symbol", tomanFormatted.contains("تومان"))
    assertTrue("USD format should contain dollar symbol", usdFormatted.contains("$"))

    // Test adding new listing
    val initialCount = viewModel.carpets.value.size
    viewModel.addNewCarpetListing(
      titleFa = "قالیچه نفیس ابریشم قم",
      originCityFa = "قم",
      stylePatternFa = "طرح درختی بهشت",
      dimensions = "۱۵۰ × ۲۰۰ سانتی‌متر",
      knotDensityRaj = 80,
      materialFa = "۱۰۰٪ ابریشم خالص",
      weaverMaster = "استاد حسینی",
      priceToman = 600_000_000L,
      storyNarrativeFa = "بافت استاد حسینی با بهترین ابریشم طبیعی"
    )
    assertEquals(initialCount + 1, viewModel.carpets.value.size)
  }

  @Test
  fun `verify star upgrade calculation math and surplus retention`() {
    // 7 blue = 1 silver, 0 remainder
    val tier1 = StarUpgradeCalculator.calculateTiers(7)
    assertEquals(1, tier1.silverStars)
    assertEquals(0, tier1.blueStars)

    // 15 blue = 2 silver (14) + 1 blue (1)
    val tier2 = StarUpgradeCalculator.calculateTiers(15)
    assertEquals(2, tier2.silverStars)
    assertEquals(1, tier2.blueStars)

    // 49 blue = 1 gold
    val tier3 = StarUpgradeCalculator.calculateTiers(49)
    assertEquals(1, tier3.goldStars)
    assertEquals(0, tier3.silverStars)
    assertEquals(0, tier3.blueStars)

    // 343 blue = 1 diamond
    val tier4 = StarUpgradeCalculator.calculateTiers(343)
    assertEquals(1, tier4.diamondStars)
    assertEquals(0, tier4.goldStars)

    // 2401 blue = 1 mythic
    val tier5 = StarUpgradeCalculator.calculateTiers(2401)
    assertEquals(1, tier5.mythicStars)

    // Composite: 2401 + 343 + 49 + 7 + 3 = 2803 blue stars
    val tierComposite = StarUpgradeCalculator.calculateTiers(2803)
    assertEquals(1, tierComposite.mythicStars)
    assertEquals(1, tierComposite.diamondStars)
    assertEquals(1, tierComposite.goldStars)
    assertEquals(1, tierComposite.silverStars)
    assertEquals(3, tierComposite.blueStars)
  }

  @Test
  fun `verify referral league stars and Tavana virtual land quota`() {
    // Top rank awards
    assertEquals(4, StarUpgradeCalculator.getReferralLeagueBonusStars(1))
    assertEquals(3, StarUpgradeCalculator.getReferralLeagueBonusStars(2))
    assertEquals(3, StarUpgradeCalculator.getReferralLeagueBonusStars(3))
    assertEquals(2, StarUpgradeCalculator.getReferralLeagueBonusStars(5))
    assertEquals(1, StarUpgradeCalculator.getReferralLeagueBonusStars(10))
    assertEquals(0, StarUpgradeCalculator.getReferralLeagueBonusStars(11))

    // Virtual land quota
    assertEquals(500, StarUpgradeCalculator.getTavanaVirtualLandQuotaSqMeters(1))
    assertEquals(450, StarUpgradeCalculator.getTavanaVirtualLandQuotaSqMeters(2))
    assertEquals(240, StarUpgradeCalculator.getTavanaVirtualLandQuotaSqMeters(10))
    assertNull(StarUpgradeCalculator.getTavanaVirtualLandQuotaSqMeters(11))
  }

  @Test
  fun `verify anti self referral rule and quota deductions in repository`() = runBlocking {
    val repo = AccountAndMarketplaceRepository()
    val user = repo.currentUser.value

    // Attempt self-referral using user's own referral code -> must fail
    val selfReferralSuccess = repo.registerReferral("09129999999", user.referralCode)
    assertFalse("Self referral should be rejected", selfReferralSuccess)

    // Valid external referral -> must succeed
    val validReferralSuccess = repo.registerReferral("09351112233", "FB-DIFF-9999")
    assertTrue("External valid referral should succeed", validReferralSuccess)

    // Test classifieds quota consumption
    val initialQuota = repo.userQuota.value
    assertTrue("Should have initial buy quota", initialQuota.canPostBuyRequest)

    val testBuyRequest = ListingItem(
      id = "test_req_1",
      ownerUserId = user.id,
      ownerName = user.fullName,
      ownerPhoneMasked = "۰۹۱۲****۵۶۷",
      ownerVerificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED,
      listingType = ListingType.BUY_REQUEST,
      fabricType = CarpetFabricType.HANDMADE,
      title = "تقاضای خرید قالیچه کاشان",
      description = "خریدار نقدی قaliچه دستباف نقشه شاه عباسی",
      province = "اصفهان",
      city = "کاشان",
      dimensions = "۲ × ۳ متر",
      priceText = "توافقی",
      status = ListingStatus.PUBLISHED
    )

    val submitted = repo.submitNewListing(testBuyRequest)
    assertTrue("Submission within quota should succeed", submitted)
    assertEquals(initialQuota.usedBuyRequests + 1, repo.userQuota.value.usedBuyRequests)
  }

  @Test
  fun `verify smart raj counter service contract behavior`() = runBlocking {
    val service = RajEstimationServiceImpl()
    val testBitmap = android.graphics.Bitmap.createBitmap(400, 400, android.graphics.Bitmap.Config.ARGB_8888)
    val quality = service.evaluateImageQuality(testBitmap)
    assertNotNull(quality)

    val result = service.analyzeRaj(com.example.model.RajAnalysisRequest(imageBitmap = testBitmap))
    assertNotNull(result)
    // When backend engine is not connected, measuredRaj must be null (never fake or fabricated)
    assertNull("Estimated raj count must be null and not fabricated without vision engine", result.measuredRaj)
    assertNotNull("Status message must explain service state", result.engineDiagnosticMessage)
  }
}
