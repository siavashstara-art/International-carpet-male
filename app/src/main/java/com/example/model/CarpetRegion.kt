package com.example.model

data class CarpetRegion(
    val id: String,
    val nameFa: String,
    val nameEn: String,
    val provinceFa: String,
    val signatureStyleFa: String,
    val signatureStyleEn: String,
    val typicalRaj: String,
    val knotSystemFa: String,
    val historyDescriptionFa: String,
    val signatureColorsFa: List<String>
)

data class CarpetInquiry(
    val id: String,
    val carpetId: String,
    val carpetTitle: String,
    val merchantName: String,
    val senderName: String,
    val messageText: String,
    val timestampFormatted: String,
    val isFromUser: Boolean
)
