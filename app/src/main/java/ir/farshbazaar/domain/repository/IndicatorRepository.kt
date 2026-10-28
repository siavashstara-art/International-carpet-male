package ir.farshbazaar.domain.repository

import ir.farshbazaar.data.local.dao.IndicatorDao
import ir.farshbazaar.data.local.entity.InspectionIndicatorEntity
import ir.farshbazaar.domain.model.InspectionIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IndicatorRepository {
    fun observeAll(): Flow<List<InspectionIndicator>>
}

class RoomIndicatorRepository(private val indicatorDao: IndicatorDao) : IndicatorRepository {
    override fun observeAll(): Flow<List<InspectionIndicator>> {
        return indicatorDao.observeAll().map { list -> list.map { it.toDomain() } }
    }
}

fun InspectionIndicatorEntity.toDomain() = InspectionIndicator(
    id = id,
    titleFa = titleFa,
    titleEn = titleEn,
    bodyFa = bodyFa,
    bodyEn = bodyEn,
    category = category,
    displayOrder = displayOrder,
    isActive = isActive,
    isDemo = isDemo
)
