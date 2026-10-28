package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleCarpetData
import com.example.model.CarpetCategory
import com.example.model.CarpetInquiry
import com.example.model.CarpetItem
import com.example.model.CarpetRegion
import com.example.model.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.text.NumberFormat
import java.util.Locale

class CarpetViewModel : ViewModel() {

    private val _carpets = MutableStateFlow<List<CarpetItem>>(SampleCarpetData.carpets)
    val carpets: StateFlow<List<CarpetItem>> = _carpets.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CarpetCategory.ALL)
    val selectedCategory: StateFlow<CarpetCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCityFilter = MutableStateFlow<String?>(null)
    val selectedCityFilter: StateFlow<String?> = _selectedCityFilter.asStateFlow()

    private val _selectedRajFilter = MutableStateFlow<Int?>(null)
    val selectedRajFilter: StateFlow<Int?> = _selectedRajFilter.asStateFlow()

    private val _currency = MutableStateFlow(Currency.TOMAN)
    val currency: StateFlow<Currency> = _currency.asStateFlow()

    // Modals / Overlays
    private val _selectedCarpetForDetail = MutableStateFlow<CarpetItem?>(null)
    val selectedCarpetForDetail: StateFlow<CarpetItem?> = _selectedCarpetForDetail.asStateFlow()

    private val _showingCertificateFor = MutableStateFlow<CarpetItem?>(null)
    val showingCertificateFor: StateFlow<CarpetItem?> = _showingCertificateFor.asStateFlow()

    private val _showingRoomPreviewFor = MutableStateFlow<CarpetItem?>(null)
    val showingRoomPreviewFor: StateFlow<CarpetItem?> = _showingRoomPreviewFor.asStateFlow()

    private val _showingMacroTextureFor = MutableStateFlow<CarpetItem?>(null)
    val showingMacroTextureFor: StateFlow<CarpetItem?> = _showingMacroTextureFor.asStateFlow()

    private val _showingDirectChatFor = MutableStateFlow<CarpetItem?>(null)
    val showingDirectChatFor: StateFlow<CarpetItem?> = _showingDirectChatFor.asStateFlow()

    private val _showingAddListingModal = MutableStateFlow(false)
    val showingAddListingModal: StateFlow<Boolean> = _showingAddListingModal.asStateFlow()

    private val _showingMemorialDialog = MutableStateFlow(false)
    val showingMemorialDialog: StateFlow<Boolean> = _showingMemorialDialog.asStateFlow()

    // Account, Classifieds, and Core Business tools
    val accountRepository = com.example.service.AccountAndMarketplaceRepository()
    val currentUser = accountRepository.currentUser
    val userQuota = accountRepository.userQuota
    val classifiedListings = accountRepository.listings
    val productCategories = accountRepository.categoryRegistry.categoriesFlow

    private val _selectedProductCategory = MutableStateFlow<String?>(null)
    val selectedProductCategory: StateFlow<String?> = _selectedProductCategory.asStateFlow()

    fun selectProductCategory(categoryId: String?) {
        _selectedProductCategory.value = categoryId
    }

    private val _showingAccountDialog = MutableStateFlow(false)
    val showingAccountDialog: StateFlow<Boolean> = _showingAccountDialog.asStateFlow()

    private val _showingCreateClassifiedDialog = MutableStateFlow(false)
    val showingCreateClassifiedDialog: StateFlow<Boolean> = _showingCreateClassifiedDialog.asStateFlow()

    private val _showingRajCounterScreen = MutableStateFlow(false)
    val showingRajCounterScreen: StateFlow<Boolean> = _showingRajCounterScreen.asStateFlow()

    private val _showingStoryMakerScreen = MutableStateFlow(false)
    val showingStoryMakerScreen: StateFlow<Boolean> = _showingStoryMakerScreen.asStateFlow()

    private val _inquiries = MutableStateFlow<List<CarpetInquiry>>(SampleCarpetData.sampleInquiries)
    val inquiries: StateFlow<List<CarpetInquiry>> = _inquiries.asStateFlow()

    val regions: List<CarpetRegion> = SampleCarpetData.regions

    // Filtered carpet list
    val filteredCarpets = combine(
        _carpets,
        _selectedCategory,
        _searchQuery,
        _selectedCityFilter,
        _selectedRajFilter
    ) { all, category, query, city, raj ->
        all.filter { item ->
            val matchesCategory = when (category) {
                CarpetCategory.ALL -> true
                CarpetCategory.MUSEUM -> item.isMasterpiece
                CarpetCategory.SILK -> item.materialFa.contains("ابریشم")
                CarpetCategory.CLASSIC -> item.originCityFa in listOf("اصفهان", "نایین", "کاشان")
                CarpetCategory.TRIBAL -> item.originCityFa in listOf("شیراز", "بیجار", "قشقایی", "ترکمن")
                CarpetCategory.ANTIQUE -> item.isFeaturedShowcase
            }
            val matchesQuery = query.isBlank() ||
                item.titleFa.contains(query, ignoreCase = true) ||
                item.originCityFa.contains(query, ignoreCase = true) ||
                item.weaverMaster.contains(query, ignoreCase = true) ||
                item.stylePatternFa.contains(query, ignoreCase = true)

            val matchesCity = city == null || item.originCityFa == city
            val matchesRaj = raj == null || item.knotDensityRaj >= raj

            matchesCategory && matchesQuery && matchesCity && matchesRaj
        }
    }

    fun selectCategory(category: CarpetCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCityFilter(city: String?) {
        _selectedCityFilter.value = city
    }

    fun setRajFilter(raj: Int?) {
        _selectedRajFilter.value = raj
    }

    fun setCurrency(currency: Currency) {
        _currency.value = currency
    }

    fun toggleFavorite(carpetId: String) {
        _carpets.value = _carpets.value.map {
            if (it.id == carpetId) it.copy(isFavorite = !it.isFavorite) else it
        }
        if (_selectedCarpetForDetail.value?.id == carpetId) {
            val current = _selectedCarpetForDetail.value
            if (current != null) {
                _selectedCarpetForDetail.value = current.copy(isFavorite = !current.isFavorite)
            }
        }
    }

    fun openDetail(carpet: CarpetItem) {
        _selectedCarpetForDetail.value = carpet
    }

    fun closeDetail() {
        _selectedCarpetForDetail.value = null
    }

    fun openCertificate(carpet: CarpetItem) {
        _showingCertificateFor.value = carpet
    }

    fun closeCertificate() {
        _showingCertificateFor.value = null
    }

    fun openRoomPreview(carpet: CarpetItem) {
        _showingRoomPreviewFor.value = carpet
    }

    fun closeRoomPreview() {
        _showingRoomPreviewFor.value = null
    }

    fun openMacroTexture(carpet: CarpetItem) {
        _showingMacroTextureFor.value = carpet
    }

    fun closeMacroTexture() {
        _showingMacroTextureFor.value = null
    }

    fun openDirectChat(carpet: CarpetItem) {
        _showingDirectChatFor.value = carpet
    }

    fun closeDirectChat() {
        _showingDirectChatFor.value = null
    }

    fun openAddListingModal() {
        _showingAddListingModal.value = true
    }

    fun closeAddListingModal() {
        _showingAddListingModal.value = false
    }

    fun openMemorialDialog() {
        _showingMemorialDialog.value = true
    }

    fun closeMemorialDialog() {
        _showingMemorialDialog.value = false
    }

    fun openAccountDialog() {
        _showingAccountDialog.value = true
    }

    fun closeAccountDialog() {
        _showingAccountDialog.value = false
    }

    fun openCreateClassifiedDialog() {
        _showingCreateClassifiedDialog.value = true
    }

    fun closeCreateClassifiedDialog() {
        _showingCreateClassifiedDialog.value = false
    }

    fun openRajCounter() {
        _showingRajCounterScreen.value = true
    }

    fun closeRajCounter() {
        _showingRajCounterScreen.value = false
    }

    fun openStoryMaker() {
        _showingStoryMakerScreen.value = true
    }

    fun closeStoryMaker() {
        _showingStoryMakerScreen.value = false
    }

    fun submitClassifiedAd(ad: com.example.model.ListingItem): Boolean {
        return accountRepository.submitNewListing(ad)
    }

    fun sendInquiry(carpetId: String, text: String, senderName: String = "شما") {
        val carpet = _carpets.value.find { it.id == carpetId } ?: return
        val newInquiry = CarpetInquiry(
            id = "inq_${System.currentTimeMillis()}",
            carpetId = carpetId,
            carpetTitle = carpet.titleFa,
            merchantName = carpet.merchantName,
            senderName = senderName,
            messageText = text,
            timestampFormatted = "هم‌اکنون",
            isFromUser = true
        )
        _inquiries.value = _inquiries.value + newInquiry
    }

    fun addNewCarpetListing(
        titleFa: String,
        originCityFa: String,
        stylePatternFa: String,
        dimensions: String,
        knotDensityRaj: Int,
        materialFa: String,
        weaverMaster: String,
        priceToman: Long,
        storyNarrativeFa: String
    ) {
        val newCarpet = CarpetItem(
            id = "c_${System.currentTimeMillis()}",
            titleFa = titleFa,
            titleEn = "New Listing: $originCityFa Masterpiece",
            originCityFa = originCityFa,
            originCityEn = originCityFa,
            stylePatternFa = stylePatternFa,
            stylePatternEn = stylePatternFa,
            dimensions = dimensions,
            knotDensityRaj = knotDensityRaj,
            knotTypeFa = if (knotDensityRaj >= 60) "گره متقارن ترکی ریزبافت" else "گره سنتی اصیل",
            knotTypeEn = "Traditional Hand-Knotted",
            materialFa = materialFa,
            materialEn = "Natural Wool & Silk",
            dyeingTypeFa = "رنگرزی سنتی گیاهی طبیعی",
            weftCountFa = "دو پوده ابریشمی اعلا",
            weaverMaster = weaverMaster,
            certificateId = "FB-${originCityFa.take(3).uppercase()}-${knotDensityRaj}00-CERT",
            priceToman = priceToman,
            priceUsd = priceToman / 60_000,
            storyNarrativeFa = storyNarrativeFa,
            storyNarrativeEn = "Handcrafted with supreme care by master weaver $weaverMaster.",
            imageResId = com.example.R.drawable.img_carpet_tabriz_1793099831642,
            isFeaturedShowcase = false,
            isMasterpiece = knotDensityRaj >= 65,
            merchantName = weaverMaster,
            merchantLocation = "$originCityFa، بازار سنتی فرش",
            merchantPhone = "+98 912 345 6789",
            merchantRating = 5.0f,
            merchantVerified = true,
            isFavorite = false
        )
        _carpets.value = listOf(newCarpet) + _carpets.value
        _showingAddListingModal.value = false
    }

    fun formatPrice(carpet: CarpetItem, currentCurrency: Currency): String {
        return when (currentCurrency) {
            Currency.TOMAN -> {
                val formatted = NumberFormat.getNumberInstance(Locale("fa", "IR")).format(carpet.priceToman)
                "$formatted تومان"
            }
            Currency.USD -> {
                val formatted = NumberFormat.getNumberInstance(Locale.US).format(carpet.priceUsd)
                "$$formatted USD"
            }
            Currency.EUR -> {
                val euros = (carpet.priceUsd * 0.92).toLong()
                val formatted = NumberFormat.getNumberInstance(Locale.GERMANY).format(euros)
                "€$formatted EUR"
            }
        }
    }
}
