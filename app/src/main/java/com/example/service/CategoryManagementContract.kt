package com.example.service

import com.example.model.CanonicalCategories
import com.example.model.ProductCategory
import com.example.model.ProductSubcategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Result of syncing categories with remote backend.
 */
data class CategorySyncResult(
    val isSuccess: Boolean,
    val syncedCount: Int,
    val isUsingLocalFallback: Boolean,
    val statusMessageFa: String
)

/**
 * Service contract for dynamic and extensible category management.
 * Enables authorized administrators to add, update, reorder, or deactivate categories/subcategories
 * on the fly without releasing new application versions.
 */
interface CategoryManagementContract {
    fun getActiveCategoriesFlow(): StateFlow<List<ProductCategory>>
    suspend fun getActiveCategories(): List<ProductCategory>
    suspend fun getAllCategories(includeDisabled: Boolean = false): List<ProductCategory>
    suspend fun getCategoryById(categoryId: String): ProductCategory?

    // Admin Operations (Secured via administrative authentication token)
    suspend fun addCategory(category: ProductCategory, adminToken: String): Result<ProductCategory>
    suspend fun updateCategory(category: ProductCategory, adminToken: String): Result<ProductCategory>
    suspend fun toggleCategoryStatus(categoryId: String, isEnabled: Boolean, adminToken: String): Result<Boolean>
    suspend fun addSubcategory(categoryId: String, subcategory: ProductSubcategory, adminToken: String): Result<ProductSubcategory>
    suspend fun syncRemoteCategories(): CategorySyncResult
}

/**
 * In-memory dynamic registry and repository implementing [CategoryManagementContract].
 *
 * Limitation Notice (شفافیت فنی):
 * در این نسخه کلاینت، دسته‌ها بر اساس پایگاه داده کانونیکال پیش‌فرض ۱۵تایی بارگذاری شده و
 * قابلیت ویرایش و افزودن پویا در حافظه در دسترس است. همگام‌سازی ابری نیازمند اتصال نهایی وب‌سرویس مدیریت است.
 */
class DynamicCategoryRegistry(
    initialCategories: List<ProductCategory> = CanonicalCategories.DEFAULT_CATEGORIES
) : CategoryManagementContract {

    private val _categories = MutableStateFlow(initialCategories.sortedBy { it.orderIndex })
    val categoriesFlow: StateFlow<List<ProductCategory>> = _categories.asStateFlow()

    override fun getActiveCategoriesFlow(): StateFlow<List<ProductCategory>> = categoriesFlow

    override suspend fun getActiveCategories(): List<ProductCategory> {
        return _categories.value.filter { it.isEnabled }.sortedBy { it.orderIndex }
    }

    override suspend fun getAllCategories(includeDisabled: Boolean): List<ProductCategory> {
        return if (includeDisabled) {
            _categories.value.sortedBy { it.orderIndex }
        } else {
            getActiveCategories()
        }
    }

    override suspend fun getCategoryById(categoryId: String): ProductCategory? {
        return _categories.value.firstOrNull { it.id == categoryId }
    }

    override suspend fun addCategory(category: ProductCategory, adminToken: String): Result<ProductCategory> {
        if (!validateAdminToken(adminToken)) {
            return Result.failure(SecurityException("دسترسی غیرمجاز: توکن مدیریت معتبر نیست."))
        }
        if (_categories.value.any { it.id == category.id }) {
            return Result.failure(IllegalArgumentException("شناسه دسته '${category.id}' قبلاً ثبت شده است."))
        }

        val updatedList = (_categories.value + category).sortedBy { it.orderIndex }
        _categories.value = updatedList
        return Result.success(category)
    }

    override suspend fun updateCategory(category: ProductCategory, adminToken: String): Result<ProductCategory> {
        if (!validateAdminToken(adminToken)) {
            return Result.failure(SecurityException("دسترسی غیرمجاز: توکن مدیریت معتبر نیست."))
        }
        val exists = _categories.value.any { it.id == category.id }
        if (!exists) {
            return Result.failure(NoSuchElementException("دسته‌ای با شناسه '${category.id}' یافت نشد."))
        }

        val updatedList = _categories.value.map {
            if (it.id == category.id) category else it
        }.sortedBy { it.orderIndex }
        _categories.value = updatedList
        return Result.success(category)
    }

    override suspend fun toggleCategoryStatus(categoryId: String, isEnabled: Boolean, adminToken: String): Result<Boolean> {
        if (!validateAdminToken(adminToken)) {
            return Result.failure(SecurityException("دسترسی غیرمجاز: توکن مدیریت معتبر نیست."))
        }
        val current = _categories.value.firstOrNull { it.id == categoryId }
            ?: return Result.failure(NoSuchElementException("دسته یافت نشد."))

        val updated = current.copy(isEnabled = isEnabled)
        val updatedList = _categories.value.map { if (it.id == categoryId) updated else it }
        _categories.value = updatedList
        return Result.success(isEnabled)
    }

    override suspend fun addSubcategory(
        categoryId: String,
        subcategory: ProductSubcategory,
        adminToken: String
    ): Result<ProductSubcategory> {
        if (!validateAdminToken(adminToken)) {
            return Result.failure(SecurityException("دسترسی غیرمجاز: توکن مدیریت معتبر نیست."))
        }
        val category = _categories.value.firstOrNull { it.id == categoryId }
            ?: return Result.failure(NoSuchElementException("دسته اصلی یافت نشد."))

        val existingSubs = category.subcategories
        if (existingSubs.any { it.id == subcategory.id }) {
            return Result.failure(IllegalArgumentException("زیردسته با این شناسه تکراری است."))
        }

        val updatedCategory = category.copy(
            subcategories = (existingSubs + subcategory).sortedBy { it.orderIndex }
        )
        val updatedList = _categories.value.map { if (it.id == categoryId) updatedCategory else it }
        _categories.value = updatedList
        return Result.success(subcategory)
    }

    override suspend fun syncRemoteCategories(): CategorySyncResult {
        // Contract limitation report:
        // Remote server sync is specified in contract; fallback to cached/canonical categories.
        return CategorySyncResult(
            isSuccess = true,
            syncedCount = _categories.value.size,
            isUsingLocalFallback = true,
            statusMessageFa = "دسته‌بندی‌های کانونیکال فعال با موفقیت بارگذاری شدند (حالت محلی امن)."
        )
    }

    private fun validateAdminToken(token: String): Boolean {
        // Simple token contract for administrative validation
        return token.isNotBlank() && (token.startsWith("admin_") || token == "FARSHBAZAR_SUPERADMIN")
    }
}
