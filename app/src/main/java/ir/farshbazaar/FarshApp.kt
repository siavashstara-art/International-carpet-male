package ir.farshbazaar

import android.content.Context
import ir.farshbazaar.core.di.AppContainer
import ir.farshbazaar.core.di.DefaultAppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class InspectionRecord(
    val id: String,
    val rugId: String,
    val applicantName: String,
    val applicantPhone: String,
    val city: String,
    val expertNotes: String,
    val submissionTime: Long = System.currentTimeMillis(),
    val status: String = "در صف بررسی هیئت کارشناسان خبره"
)

class FarshApp(context: Context) {
    val container: AppContainer = DefaultAppContainer(context)
    val currentUserId: String = "user-self"

    private val _inspections = MutableStateFlow<List<InspectionRecord>>(emptyList())
    val inspections: StateFlow<List<InspectionRecord>> = _inspections.asStateFlow()

    fun submitInspection(
        rugId: String,
        applicantName: String,
        applicantPhone: String,
        city: String,
        notes: String
    ): Boolean {
        val newRecord = InspectionRecord(
            id = "insp_${System.currentTimeMillis()}",
            rugId = rugId,
            applicantName = applicantName,
            applicantPhone = applicantPhone,
            city = city,
            expertNotes = notes
        )
        _inspections.value = listOf(newRecord) + _inspections.value
        return true
    }
}
