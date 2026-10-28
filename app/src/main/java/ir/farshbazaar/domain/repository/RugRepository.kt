package ir.farshbazaar.domain.repository

import ir.farshbazaar.data.local.dao.IndicatorDao
import ir.farshbazaar.data.local.dao.InteractionDao
import ir.farshbazaar.data.local.dao.RugDao
import ir.farshbazaar.data.local.entity.ChecklistStateEntity
import ir.farshbazaar.data.local.entity.RugEntity
import ir.farshbazaar.domain.model.Category
import ir.farshbazaar.domain.model.InspectionIndicator
import ir.farshbazaar.domain.model.Rug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RugRepository {
    fun observeAll(): Flow<List<Rug>>
    fun observeByCategory(category: Category): Flow<List<Rug>>
    fun observeBySeller(sellerId: String): Flow<List<Rug>>
    fun observe(id: String): Flow<Rug?>
    fun search(query: String): Flow<List<Rug>>
    fun observeIndicators(): Flow<List<InspectionIndicator>>
    fun observeChecklist(rugId: String): Flow<Set<String>>
    suspend fun setChecklist(rugId: String, indicatorId: String, checked: Boolean)
}

class RoomRugRepository(
    private val rugDao: RugDao,
    private val indicatorDao: IndicatorDao,
    private val interactionDao: InteractionDao
) : RugRepository {

    override fun observeAll(): Flow<List<Rug>> {
        return rugDao.observeAll().map { list -> list.map { it.toDomain() } }
    }

    override fun observeByCategory(category: Category): Flow<List<Rug>> {
        return rugDao.observeByCategory(category.id).map { list -> list.map { it.toDomain() } }
    }

    override fun observeBySeller(sellerId: String): Flow<List<Rug>> {
        return rugDao.observeBySeller(sellerId).map { list -> list.map { it.toDomain() } }
    }

    override fun observe(id: String): Flow<Rug?> {
        return rugDao.observe(id).map { it?.toDomain() }
    }

    override fun search(query: String): Flow<List<Rug>> {
        return rugDao.search(query.trim()).map { list -> list.map { it.toDomain() } }
    }

    override fun observeIndicators(): Flow<List<InspectionIndicator>> {
        return indicatorDao.observeAll().map { list -> list.map { it.toDomain() } }
    }

    override fun observeChecklist(rugId: String): Flow<Set<String>> {
        return interactionDao.observeChecklist(rugId).map { list ->
            list.filter { it.checked }.map { it.indicatorId }.toSet()
        }
    }

    override suspend fun setChecklist(rugId: String, indicatorId: String, checked: Boolean) {
        interactionDao.insertChecklistState(
            ChecklistStateEntity(
                rugId = rugId,
                indicatorId = indicatorId,
                checked = checked,
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}

fun RugEntity.toDomain(): Rug {
    val cat = Category.fromId(category)
    return Rug(
        id = id,
        sellerId = sellerId,
        title = title,
        description = description,
        category = cat,
        region = region,
        era = era,
        price = price,
        createdAt = createdAt,
        isDemo = isDemo,
        raj = when (region) {
            "تبریز" -> 70
            "اصفهان" -> 65
            "قم" -> 80
            "نایین" -> 60
            "هریس" -> 45
            else -> 50
        },
        knotType = if (region in listOf("تبریز", "هریس")) "گره ترکی (متقارن)" else "گره فارسی (نامتقارن)",
        materials = when (cat) {
            Category.HANDMADE_RUG -> if (region == "قم") "۱۰۰٪ ابریشم خالص دورو" else "چله ابریشم، پرز کرک و ابریشم"
            Category.MACHINE_RUG -> "الیاف اکریلیک هیت‌ست آلمان"
            Category.SERVICES -> "تجهیزات و شوینده‌های گیاهی سنتی"
            else -> "دست‌ساز ایرانی"
        },
        dimensions = "۲ × ۳ متر (۶ متری)"
    )
}
